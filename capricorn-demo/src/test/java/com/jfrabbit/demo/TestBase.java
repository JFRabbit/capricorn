package com.jfrabbit.demo;

import com.jfrabbit.demo.caze.DemoCase;

import javax.annotation.Resource;

/**
 * extends base class, declare java bean, define constant or common function
 * 继承测试基类、声明java bean、定义常量和公共方法
 *
 * @author JasonZhang 2019-03-22 17:56
 */
public class TestBase extends DemoRestApiTest {

    @Resource
    protected DemoCase demoCase;
}
