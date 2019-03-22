package com.jfrabbit.test.api.annotation;

import com.jfrabbit.common.rest.HttpMethodEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author JasonZhang 2018/6/8 下午5:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Request {

    /*请求模块的URL*/
    String mapping() default "";

    /*请求的URL*/
    String url() default "";

    /*请求的方法*/
    HttpMethodEnum method() default HttpMethodEnum.GET;

}
