package com.jfrabbit.test.api;

import com.jfrabbit.common.rest.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JasonZhang 2018/9/2 下午5:28
 */
public interface ParamUtil {

    default Map<String, String> singleMapParam(String key, String value) {
        return new HashMap<String, String>() {
            {
                this.put(key, value);
            }
        };
    }

    default <T> T[] arrayBuilder(T... t) {
        return t;
    }

    default Map<String, Object> muiltiMapParam(String[] keys, String[] values) {
        if (keys.length == 0 || (keys.length != values.length)) {
            throw new RuntimeException("Keys and Values's Length must equals");
        }
        return new HashMap<String, Object>() {
            {
                for (int i = 0; i < keys.length; i++) {
                    this.put(keys[i], values[i]);
                }
            }
        };
    }

    default HttpResponse response(int code) {
        return new HttpResponse(code);
    }

    default HttpResponse _200() {
        return response(200);
    }
}
