package com.jfrabbit.common.rest.util;

import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * 将对象或Map设置到Get请求的URL当中
 *
 * @author JasonZhang
 */
public class URLUtil {

    public static String setURL(String url, Map<String, String> paramEntiy) throws URISyntaxException, MalformedURLException {
        URIBuilder builder = new URIBuilder(url);

        paramEntiy.forEach((k, v) -> builder.addParameter(k, String.valueOf(v)));

        return builder.build().toURL().toString();
    }

    public static Builder builder(String url) throws URISyntaxException {
        return new Builder(url);
    }

    public static class Builder {

        private URIBuilder builder;

        public Builder(String url) throws URISyntaxException {
            builder = new URIBuilder(url);
        }

        public Builder setParam(String key, Object value) {
            this.builder.addParameter(key, String.valueOf(value));
            return this;
        }

        public String build() throws URISyntaxException, MalformedURLException {
            return this.builder.build().toURL().toString();
        }
    }
}
