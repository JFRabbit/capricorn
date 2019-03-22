package com.jfrabbit.test.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author JasonZhang 2018/6/9 下午6:06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ApiTestCase {

    /* 测试用例名称 */
    String value() default "";

    /* 测试用例前缀*/
    String prefix() default "";
}
