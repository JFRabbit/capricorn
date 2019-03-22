package com.jfrabbit.common.rest.util.compare;

/**
 * @author JasonZhang 2018/9/28 上午10:07
 */
public interface JsonCompare {

    CompareResult compareJson(String expect, String actual);
}
