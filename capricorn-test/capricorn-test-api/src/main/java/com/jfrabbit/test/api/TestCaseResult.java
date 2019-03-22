package com.jfrabbit.test.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

/**
 * @author JasonZhang 2019-01-21 11:18
 */
@Getter
@Setter
@AllArgsConstructor
public class TestCaseResult {

    private int caseIndex;
    private String caseName;
    private ResultEnum result;
    private String throwable;

    @Override
    public String toString() {
        return this.getCaseIndex() + ":" + String.format("%-4s", this.result.getColor()) + this.getCaseName();
    }

    public void debug(Logger logger) {
        logger.debug(this.toString());
    }
}
