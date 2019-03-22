package com.jfrabbit.common.rest;

import com.google.gson.Gson;
import com.jfrabbit.common.util.FunctionUtil;
import com.jfrabbit.common.util.json.JsonReplace;
import com.jfrabbit.common.util.json.JsonReplaceResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JasonZhang 2018/9/27 下午6:02
 */
@Slf4j
public class HttpUtil implements FunctionUtil {

    public static HttpResponse execute(HttpRequest request) {

        HttpResponse response = new HttpResponse();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        try {

            URIBuilder builder;
            if (NOT_EMPTY_STR.test(request.getUrl())) {
                builder = new URIBuilder(request.getUrl());
            } else {
                throw new RuntimeException("Not set Http Url!");
            }

            HttpRequestBase httpRequestBase = null;

            if (NOT_EMPTY_MAP.test(request.getParam())) {
                builder = setParam(request.getParam(), builder);
            }

            if (NOT_NULL.test(request.getMethod())) {
                switch (request.getMethod()) {
                    case GET:
                        httpRequestBase = new HttpGet(builder.build());
                        break;
                    case POST:
                        httpRequestBase = new HttpPost(builder.build());
                        break;
                    case DELETE:
                        httpRequestBase = new HttpDelete(builder.build());
                        break;
                    case PUT:
                        httpRequestBase = new HttpPut(builder.build());
                        break;
                }
            } else {
                throw new RuntimeException("Not set Http Method!");
            }

            if (NOT_NULL.test(request.getPayload())) {

                if (!(request.getPayload() instanceof JsonReplace)) {
                    request.setEntity(new StringEntity(new Gson().toJson(request.getPayload()), "UTF-8"));
                } else {
                    request.setEntity(new StringEntity(JsonReplaceResolver.resolve(request.getPayload()), "UTF-8"));
                }
            }

            if (NOT_NULL.test(request.getEntity())) {
                if (httpRequestBase instanceof HttpPost) {
                    ((HttpPost) httpRequestBase).setEntity(request.getEntity());
                } else if (httpRequestBase instanceof HttpPut) {
                    ((HttpPut) httpRequestBase).setEntity(request.getEntity());
                }
                // NOTE : not support GET and DELETE with payload
            }

            httpRequestBase.setHeaders(setHeaders(request.getHeader()));

            long start = System.currentTimeMillis();
            org.apache.http.HttpResponse httpResponse = closeableHttpClient.execute(httpRequestBase, request.getContext());
            long end = System.currentTimeMillis();

            response.setSuccess(true);
            response.setTime(end - start);
            response.setCode(httpResponse.getStatusLine().getStatusCode());
            response.setHeader(setHeadersMap(httpResponse.getAllHeaders()));
            if (NOT_NULL.test(httpResponse.getEntity())) {
                response.setContent(getContent(httpResponse.getEntity().getContent()));
            }

        } catch (URISyntaxException | IOException e) {
            log.error("Error Happened when execute http rest, exception:{}", e);
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    private static URIBuilder setParam(Map<String, String> param, URIBuilder builder) {
        param.forEach(builder::addParameter);
        return builder;
    }

    private static Header[] setHeaders(Map<String, String> headersMap) {
        List<BasicHeader> headerList = new ArrayList<>();
        headersMap.forEach((k, v) -> headerList.add(new BasicHeader(k, v)));

        Header[] headers = new Header[headerList.size()];
        for (int i = 0; i < headerList.size(); i++) {
            headers[i] = headerList.get(i);
        }
        return headers;
    }

    private static Map<String, String> setHeadersMap(Header[] headersArray) {
        Map<String, String> map = new HashMap<>();
        for (Header header : headersArray) {
            map.put(header.getName(), header.getValue());
        }
        return map;
    }

    private static String getContent(InputStream stream) throws IOException {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
}
