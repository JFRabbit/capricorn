package com.jfrabbit.common.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建实例对象获取目标类的值，若通过反射获取，类属性必须设定为static
 *
 * @author JasonZhang 2018/10/11 下午2:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Yml {

    /**
     * @return multi Yml file path
     */
    String[] path() default {};

    /**
     * @return Yml value expressions
     */
    String value() default "";

}
