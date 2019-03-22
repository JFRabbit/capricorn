package com.jfrabbit.test.api.annotation;

import com.jfrabbit.common.rest.HttpMethodEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author JasonZhang 2018/6/9 下午6:31
 */
public class RequestInjectResolver {

    private static final Logger logger = LoggerFactory.getLogger(RequestInjectResolver.class);

    public static String getMapping(Method method) {
        if (method.getDeclaringClass().isAnnotationPresent(Request.class)) {
            String mapping = method.getDeclaringClass().getAnnotation(Request.class).mapping();
            logger.debug("Request mapping:{}", mapping);
            return mapping;
        }
        return "";
    }

    public static String getUrl(Method method) {
        if (method.isAnnotationPresent(Request.class)) {
            String url = method.getAnnotation(Request.class).url();
            logger.debug("Url:{}", url);
            return url;
        }
        throw new RuntimeException("Can't get Url");
    }

    public static HttpMethodEnum getMethod(Method method) {
        if (method.isAnnotationPresent(Request.class)) {
            HttpMethodEnum requestMethod = method.getAnnotation(Request.class).method();
            logger.debug("RequestMethod:{}", requestMethod);
            return requestMethod;
        }
        throw new RuntimeException("Can't get Url");
    }

}
