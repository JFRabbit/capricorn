package com.jfrabbit.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.jfrabbit.common.util.json.GsonUtil;
import com.jfrabbit.common.util.json.JsonPathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class YamlUtil {

    private static Logger logger = LoggerFactory.getLogger(YamlUtil.class);

    public static MapChain chain(Object object) {
        return new MapChain(object);
    }

    public static class MapChain {

        private Map<String, Object> chain;

        @SuppressWarnings("unchecked")
        private MapChain(Object object) {
            if (object == null) {
                throw new NullPointerException("Can't instance MapChain by param: null");
            }
            if (object instanceof Map) {
                this.chain = (Map<String, Object>) object;
            } else {
                throw new ClassCastException("Object: [" + object.getClass() + "] cannot be cast to java.util.Map");
            }
        }

        public Object value(String key) {
            return this.chain.get(key);
        }

        public MapChain asMap(String key) {
            return new MapChain(this.chain.get(key));
        }

        public <T> List<T> asList(String key) {
            return Arrays.asList((T[]) this.chain.get(key));
        }

        public String asText(String key) {
            return String.valueOf(value(key));
        }

        public int asInt(String key) {
            return Integer.valueOf(String.valueOf(value(key)));
        }

        public boolean asBool(String key) {
            return Boolean.valueOf(String.valueOf(value(key)));
        }

    }

    public static Object load(String path) {
        try {
            return new Yaml().load(new FileInputStream(new File(path)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Iterable<Object> loadAll(String path) {
        try {
            return new Yaml().loadAll(new FileInputStream(new File(path)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T loadAs(String path, Class<T> clazz) {
        try {
            return new Yaml().loadAs(new FileInputStream(new File(path)), clazz);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String loadAsJsonStr(String path) {
        return new Gson().toJson(load(path));
    }

    private static String getPath(String path) {
        String jsonStr;
        String ab_path;
        try {
            ab_path = ClassLoaderUtil.getClassLoaderResource(path);
            jsonStr = YamlUtil.loadAsJsonStr(ClassLoaderUtil.getClassLoaderResource(path));
            logger.info(ab_path);
        } catch (Exception e) {
            logger.warn("Load from resources failed:{}, try to load local!", e.getMessage());
            ab_path = new File("").getAbsolutePath() + "/" + path;
            jsonStr = YamlUtil.loadAsJsonStr(ab_path);
            logger.info(ab_path);
        }
        return jsonStr;
    }

    public static void injectYamlField(Object target) {
        if (target.getClass().isAnnotationPresent(Yml.class)) {
            String[] paths = target.getClass().getAnnotation(Yml.class).path();
            logger.info("Yaml paths:{}", Arrays.asList(paths));

            String jsonStr = "";

            for (String path : paths) {
                try {
                    jsonStr = getPath(path);
                    break;
                } catch (Exception e) {
                    logger.warn("Load from local failed:{}, try to load another!", e.getMessage());
                }
            }

            if (jsonStr.equals("")) {
                throw new RuntimeException("Not found yaml file in resourcePath: " + Arrays.asList(paths));
            }

            logger.info(GsonUtil.jsonStrFormat(jsonStr));

            setFieldValueByYaml(target, jsonStr);

            ReflectUtil.forField(target);
        } else {
            throw new RuntimeException("Not set yaml path in class!");
        }
    }


    private static void setFieldValueByYaml(Object target, String jsonStr) {
        JsonPathUtil jsonPathUtil = new JsonPathUtil();

        JsonNode node;
        for (Field field : target.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Yml.class)) {
                field.setAccessible(true);

                try {
                    node = jsonPathUtil.nodeValue(field.getAnnotation(Yml.class).value(), jsonStr);
                    ReflectUtil.setField(
                            field,
                            target,
                            jsonPathUtil.as(node)
                    );
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }

            }
        }
    }

    public static void dump(Object object, String path) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            new Yaml().dump(object, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
