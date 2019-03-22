package com.jfrabbit.test.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author JasonZhang 2018/6/9 下午6:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Env {

    /*环境变量参数类*/
    Class env() default Object.class;

    /*环境变量的参数名*/
    String value() default "";
}
