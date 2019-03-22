package com.jfrabbit.common.rest.util.compare;

import com.jfrabbit.common.rest.HttpResponse;

/**
 * @author JasonZhang 2018/9/28 上午9:28
 */
public interface HttpResponseCompare {

    CompareResult compareHttpResponse(HttpResponse expect, HttpResponse actual);
}
