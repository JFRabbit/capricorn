package com.jfrabbit.demo;

import com.jfrabbit.common.util.json.JsonReplaceParam;

/**
 * @author JasonZhang 2019-03-22 18:00
 */
public class FooResponseCode {
    private int code;
    private Object data;
    private String info;
    private String message;

    @JsonReplaceParam("json.param")
    private String json_param;

    public FooResponseCode(int code, Object data, String info, String message, String json_param) {
        this.code = code;
        this.data = data;
        this.info = info;
        this.message = message;
        this.json_param = json_param;
    }
}
