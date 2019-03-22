package com.jfrabbit.test.api;

import com.jfrabbit.common.util.ConsoleColors;

/**
 * @author JasonZhang 2019-01-21 11:20
 */
public enum ResultEnum {
    PASS(ConsoleColors.PASS),
    FAIL(ConsoleColors.FAIL),
    SKIP(ConsoleColors.SKIP);

    private String color;

    ResultEnum(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
