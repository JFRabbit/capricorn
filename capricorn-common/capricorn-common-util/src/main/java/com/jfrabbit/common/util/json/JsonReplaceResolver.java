package com.jfrabbit.common.util.json;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JasonZhang 2018/10/19 下午5:29
 */
public class JsonReplaceResolver {

    public static String resolve(Object object) {
        Map<String, String> replaceMap = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonReplaceParam.class)) {
                replaceMap.put(field.getName(), field.getAnnotation(JsonReplaceParam.class).value());
            }
        }
        String json = new Gson().toJson(object);
        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            json = json.replace(entry.getKey(), entry.getValue());
        }

        return json;
    }
}
