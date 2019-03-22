package com.jfrabbit.common.util.json;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class GsonUtil {

    private static final Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * Json格式字符串格式化
     *
     * @param jsonObject Json对象
     * @return Json对象格式化输出
     */
    public static String jsonObjFormat(JsonObject jsonObject) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject);
    }

    public static String jsonStrFormat(String str) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Param is empty");
        }
        JsonElement element = strToJsonElement(str);
        if (element.isJsonObject()) {
            return "\n" + jsonObjFormat(element.getAsJsonObject()) + "\n";
        } else {
            throw new IllegalArgumentException(str + "\t Is not a Json Object");
        }
    }

    public static JsonElement strToJsonElement(String str) {
        return new JsonParser().parse(str);
    }

    public static GsonTypeEnum checkJsonType(JsonElement jsonElement) {
        if (jsonElement == null) {
            return null;
        }
        if (jsonElement.isJsonObject()) {
            return GsonTypeEnum.OBJECT;
        }
        if (jsonElement.isJsonArray()) {
            return GsonTypeEnum.ARRAY;
        }
        if (jsonElement.isJsonPrimitive()) {
            return GsonTypeEnum.PRIMITIVE;
        }
        if (jsonElement.isJsonNull()) {
            return GsonTypeEnum.NULL;
        }
        return null;
    }

    public static boolean isSameJsonType(JsonElement expect, JsonElement other) {
        return expect != null && checkJsonType(expect).equals(checkJsonType(other));
    }

    /**
     * 从文件当中读取并专成JsonElement对象
     *
     * @param jsonFilePath Json文件路径
     * @return JsonElement
     */
    public static JsonElement getJsonObjFromJsonFile(String jsonFilePath) {
        BufferedReader reader = null;
        JsonElement jsonElement = null;
        try {
            reader = new BufferedReader(new FileReader(new File(jsonFilePath)));
            jsonElement = gson.fromJson(reader, JsonElement.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonElement;
    }

    public static void write2JsonFile(Object obj, String filePath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Writer writer = new FileWriter(filePath);
        gson.toJson(obj, writer);
        writer.close();
    }
}
