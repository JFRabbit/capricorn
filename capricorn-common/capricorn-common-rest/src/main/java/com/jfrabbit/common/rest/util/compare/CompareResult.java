package com.jfrabbit.common.rest.util.compare;

import com.jfrabbit.common.rest.HttpResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JasonZhang 2018/9/28 上午9:29
 */
@Getter
@Setter
@ToString
public class CompareResult {
    private boolean success;
    private List<String> errorMsg;
    private HttpResponse expect;
    private HttpResponse actual;

    public CompareResult() {
        this.success = true;
        this.errorMsg = new ArrayList<>();
        this.expect = new HttpResponse();
        this.actual = new HttpResponse();
    }

    public CompareResult fail() {
        this.setSuccess(false);
        return this;
    }

    public CompareResult msg(String msg) {
        this.getErrorMsg().add(msg);
        return this;
    }

    public String error() {
        StringBuilder builder = new StringBuilder();
        errorMsg.forEach(builder::append);
        return builder.toString();
    }
}
