package com.jfrabbit.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;

/**
 * @author JasonZhang 2019-02-27 14:26
 */
@Lazy // default Lazy type 默认全局为Lazy模式
@Configuration
@ComponentScan(basePackages = {
        "com.jfrabbit.demo"
})
@EnableAspectJAutoProxy
public class DemoApiSpringConfig {
}
