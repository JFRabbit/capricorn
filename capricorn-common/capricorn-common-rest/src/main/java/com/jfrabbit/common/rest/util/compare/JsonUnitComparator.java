package com.jfrabbit.common.rest.util.compare;

import com.jfrabbit.common.rest.HttpResponse;
import com.jfrabbit.common.util.FunctionUtil;
import lombok.Getter;
import lombok.Setter;
import net.javacrumbs.jsonunit.JsonAssert;
import net.javacrumbs.jsonunit.core.Option;

import java.util.Arrays;

/**
 * @author JasonZhang 2018/9/28 上午9:57
 */
@Getter
@Setter
public class JsonUnitComparator extends HttpComparator implements JsonCompare, FunctionUtil {

    private Option[] options;

    public JsonUnitComparator(Option... options) {
        this.options = options;
    }

    @Override
    public CompareResult compareHttpResponse(HttpResponse expect, HttpResponse actual) {

        this.result.setExpect(expect);
        this.result.setActual(actual);

        if (expect.getCode() != 0) {
            super.compareCode(expect.getCode(), actual.getCode());
        }

        if (NOT_EMPTY_STR.test(expect.getContent())) {
            this.compareJson(expect.getContent(), actual.getContent());
        }

        return this.getResult();
    }

    @Override
    public CompareResult compareJson(String expect, String actual) {

        try {
            JsonAssert.setOptions(Option.IGNORING_ARRAY_ORDER, this.getOptions());
            JsonAssert.assertJsonEquals(expect, actual);
        } catch (AssertionError e) {
            // XXX 此处是否返回更细致的内容
            this.getResult().fail().msg(e.getMessage());
        } finally {
            JsonAssert.resetOptions();
        }

        return this.getResult();
    }

    @Override
    public String toString() {
        return "JsonUnitComparator{" +
                "options=" + Arrays.toString(options) +
                "} " + super.toString();
    }
}
