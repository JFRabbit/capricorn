package com.jfrabbit.test.api;

import com.jfrabbit.common.rest.HttpRequest;
import com.jfrabbit.common.rest.HttpResponse;
import com.jfrabbit.common.rest.util.compare.CompareResult;
import com.jfrabbit.common.rest.util.compare.JsonUnitComparator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JasonZhang 2018/6/9 下午1:53
 */
@Getter
@Setter
@NoArgsConstructor
public class RestRequestParam {

    private String testcase;
    private Map<String, String> header;
    private Map<String, String> params;
    private Map<String, String> dynamicParams;
    private Object payload;
    private HttpRequest request;
    private String[] multipart;
    private HttpResponse response;
    private JsonUnitComparator comparator = new JsonUnitComparator();
    private CompareResult compareResult;

    public final RestRequestParam testcase(String testcase) {
        this.setTestcase(testcase);
        return this;
    }

    public final RestRequestParam header(Map<String, String> header) {
        this.setHeader(header);
        return this;
    }

    public final RestRequestParam header(String key, String value) {
        if (null == this.getHeader()) {
            this.setHeader(new HashMap<>());
        }
        this.getHeader().put(key, value);
        return this;
    }

    public final RestRequestParam upload(String filePath, String key) {
        this.setMultipart(new String[2]);
        this.getMultipart()[0] = filePath;
        this.getMultipart()[1] = key;
        return this;
    }

    public final RestRequestParam upload(String filePath, String key, String contentType) {
        this.setMultipart(new String[3]);
        this.getMultipart()[0] = filePath;
        this.getMultipart()[1] = key;
        this.getMultipart()[2] = contentType;
        return this;
    }

    public final RestRequestParam params(Map<String, String> params) {
        this.setParams(params);
        return this;
    }

    public final RestRequestParam dynamic(Map<String, String> dynamic) {
        this.setDynamicParams(dynamic);
        return this;
    }

    public final RestRequestParam payload(Object payload) {
        this.setPayload(payload);
        return this;
    }

    public final RestRequestParam request(HttpRequest request) {
        this.setRequest(request);
        return this;
    }

    public final RestRequestParam response(HttpResponse response) {
        this.setResponse(response);
        return this;
    }

    public final RestRequestParam comparator(JsonUnitComparator comparator) {
        this.setComparator(comparator);
        return this;
    }


    @Override
    public String toString() {
        return "RestRequestParam{" +
                "testcase='" + testcase + '\'' +
                ", header=" + header +
                ", params=" + params +
                ", dynamicParams=" + dynamicParams +
                ", payload=" + payload +
                ", request=" + request +
                ", multipart=" + Arrays.toString(multipart) +
                ", response=" + response +
                ", comparator=" + comparator +
                ", compareResult=" + compareResult +
                '}';
    }
}
