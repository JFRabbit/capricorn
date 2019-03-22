package com.jfrabbit.test.api.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author JasonZhang 2018/6/9 下午7:16
 */
public class ApiTestCaseInjectResolver {

    private static final Logger logger = LoggerFactory.getLogger(ApiTestCaseInjectResolver.class);

    public static String getCasePrefix(Method method) {
        if (method.getDeclaringClass().isAnnotationPresent(ApiTestCase.class)) {
            String prefix = method.getDeclaringClass().getAnnotation(ApiTestCase.class).prefix();
            logger.debug("TestCase prefix:{}", prefix);
            return prefix;
        }
        return "";
    }

    public static String getCaseName(Method method) {
        if (method.isAnnotationPresent(ApiTestCase.class)) {
            String caseName = method.getAnnotation(ApiTestCase.class).value();
            logger.debug("TestCaseName:{}", caseName);
            return caseName;
        }
        throw new RuntimeException("Can't get ApiTestCase name");
    }
}
