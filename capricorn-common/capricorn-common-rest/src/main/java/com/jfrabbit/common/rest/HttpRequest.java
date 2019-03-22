package com.jfrabbit.common.rest;

import com.jfrabbit.common.util.FunctionUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.http.HttpEntity;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JasonZhang 2018/9/27 下午5:51
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HttpRequest {

    private String url;
    private HttpMethodEnum method;
    private Map<String, String> header = new HashMap<>();
    private Map<String, String> param = new HashMap<>();
    private Object payload;
    private HttpEntity entity;
    private HttpClientContext context;

    public HttpRequest url(String url) {
        this.setUrl(url);
        return this;
    }

    public HttpRequest method(HttpMethodEnum method) {
        this.setMethod(method);
        return this;
    }

    public HttpRequest header(Map<String, String> header) {
        if (FunctionUtil.NOT_EMPTY_MAP.test(header)) {
            this.getHeader().putAll(header);
        }
        return this;
    }

    public HttpRequest header(String k, String v) {
        this.getHeader().put(k, v);
        return this;
    }

    public HttpRequest multipart(String filePath, String key) {
        String[] paths = filePath.split(";");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        for (String path : paths) {
            builder.addPart(key, new FileBody(new File(path), ContentType.MULTIPART_FORM_DATA));
        }
        this.setEntity(builder.build());

        return this;
    }

    public HttpRequest multipart(String filePath, String key, String contentType) {
        String[] paths = filePath.split(";");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        for (String path : paths) {
            builder.addPart(key, new FileBody(new File(path), ContentType.create(contentType)));
        }
        this.setEntity(builder.build());
        return this;
    }

    public HttpRequest param(Map<String, String> param) {
        this.getParam().putAll(param);
        return this;
    }

    public HttpRequest param(String k, String v) {
        this.getParam().put(k, v);
        return this;
    }

    public HttpRequest payload(Object payload) {
        this.setPayload(payload);
        return this;
    }

}
