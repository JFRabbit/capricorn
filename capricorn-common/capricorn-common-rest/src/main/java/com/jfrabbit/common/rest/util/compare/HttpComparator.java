package com.jfrabbit.common.rest.util.compare;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author JasonZhang 2018/9/28 上午9:40
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class HttpComparator implements HttpResponseCompare {

    protected CompareResult result;

    public CompareResult compareCode(int expect, int actual) {
        if (expect != actual) {
            return result.fail().msg("HTTP CODE DIFF: expect " + expect + ", actual " + actual);
        }
        return result;
    }

}
