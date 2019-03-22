package com.jfrabbit.common.rest;

import com.jfrabbit.common.util.json.GsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author JasonZhang 2018/9/27 下午5:52
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HttpResponse {

    private int code;
    private String content;
    private long time;
    private Map<String, String> header;
    private boolean success = false;

    public HttpResponse(int code) {
        this.code = code;
    }

    public HttpResponse(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public HttpResponse(int code, Object content) {
        this.code = code;
        this.content = GsonUtil.toJson(content);
    }

    public String debug(boolean isExpect) {
        String title = isExpect ? "Expect:\n" : "Actual:\n";
        return title +
                "\tcode : " + code + "\n" +
                "\tcontent : " + content + "\n";
    }
}
