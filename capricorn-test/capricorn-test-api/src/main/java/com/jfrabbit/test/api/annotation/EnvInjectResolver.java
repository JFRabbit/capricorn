package com.jfrabbit.test.api.annotation;

import com.jfrabbit.common.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author JasonZhang 2018/6/9 下午6:31
 */
public class EnvInjectResolver {

    private static final Logger logger = LoggerFactory.getLogger(EnvInjectResolver.class);

    public static String getEnv(Method method) {
        if (method.getDeclaringClass().isAnnotationPresent(Env.class)) {
            Class clazz = method.getDeclaringClass().getAnnotation(Env.class).env();
            String key = method.getDeclaringClass().getAnnotation(Env.class).value();
            for (Field field : clazz.getDeclaredFields()) {
                ReflectUtil.setAccessible(field);

                if (key.equals(field.getName())) {
                    String env = ReflectUtil.getField(field, clazz).toString();
                    logger.debug("Env:{}", env);
                    return env;
                }
            }
        }
        throw new RuntimeException("Not set @Env or can't get value of 'env'");
    }
}
