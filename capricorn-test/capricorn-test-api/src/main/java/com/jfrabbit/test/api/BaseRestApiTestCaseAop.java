package com.jfrabbit.test.api;

import com.google.common.truth.Truth;
import com.jfrabbit.common.rest.HttpMethodEnum;
import com.jfrabbit.common.rest.HttpRequest;
import com.jfrabbit.common.rest.HttpResponse;
import com.jfrabbit.common.rest.HttpUtil;
import com.jfrabbit.common.rest.util.URLUtil;
import com.jfrabbit.common.rest.util.compare.CompareResult;
import com.jfrabbit.common.rest.util.compare.JsonUnitComparator;
import com.jfrabbit.common.util.AopUtil;
import com.jfrabbit.common.util.FunctionUtil;
import com.jfrabbit.test.api.annotation.ApiTestCaseInjectResolver;
import com.jfrabbit.test.api.annotation.EnvInjectResolver;
import com.jfrabbit.test.api.annotation.RequestInjectResolver;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JasonZhang 2018/7/2 下午2:32
 */
@Slf4j
public abstract class BaseRestApiTestCaseAop implements FunctionUtil {

    protected static final String AOP_RULE_PREFIX = "execution(public com.jfrabbit.common.rest.util.compare.CompareResult ";

    protected Object testcaseAspect(ProceedingJoinPoint p) throws Throwable {
        return doRequest(p);
    }

    private CompareResult doRequest(ProceedingJoinPoint p) throws NoSuchMethodException {
        Method method = AopUtil.getMethodFromProceedingJoinPoint(p);

        String env = EnvInjectResolver.getEnv(method);
        String mapping = RequestInjectResolver.getMapping(method);
        String url = RequestInjectResolver.getUrl(method);
        HttpMethodEnum methodEnum = RequestInjectResolver.getMethod(method);


        String caseName = ApiTestCaseInjectResolver.getCaseName(method);
        String prefix = ApiTestCaseInjectResolver.getCasePrefix(method);

        RestRequestParam requestParam = new RestRequestParam();
        for (Object object : p.getArgs()) {
            if (object instanceof RestRequestParam) {
                requestParam = (RestRequestParam) object;
            }
        }

        log.debug("{}", requestParam);

        HttpRequest request;

        if (NOT_EMPTY_MAP.test(requestParam.getDynamicParams())) {
            for (Map.Entry<String, String> entry : requestParam.getDynamicParams().entrySet()) {
                if (entry.getValue() == null) {
                    throw new RuntimeException();
                }
                url = url.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }

        if (null == requestParam.getPayload()) {
            request = new HttpRequest().url(env + mapping + url).method(methodEnum);
        } else {
            request = new HttpRequest().url(env + mapping + url).method(methodEnum).payload(requestParam.getPayload());
        }

        if (NOT_EMPTY_MAP.test(requestParam.getParams())) {
            try {
                request.url(URLUtil.setURL(request.getUrl(), requestParam.getParams()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        setLoginToken(request, requestParam);

        request.header(requestParam.getHeader());

        if (requestParam.getMultipart() != null) {
            if (requestParam.getMultipart().length == 2) {
                request.multipart(requestParam.getMultipart()[0], requestParam.getMultipart()[1]);
            } else if (requestParam.getMultipart().length == 3) {
                request.multipart(requestParam.getMultipart()[0], requestParam.getMultipart()[1], requestParam.getMultipart()[2]);
            }
        } else {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            request.header(headers);
        }

        requestParam.setRequest(request);

        if (requestParam.getTestcase() == null) {
            requestParam.setTestcase(prefix + caseName);
        } else {
            requestParam.setTestcase(prefix + requestParam.getTestcase());
        }

        if (requestParam.getResponse() == null) {
            throw new RuntimeException("Must set RequestResponse!");
        }

        return this.doTest(requestParam.getTestcase(), request, requestParam.getResponse(), requestParam.getComparator());
    }

    abstract protected void setLoginToken(HttpRequest request, RestRequestParam requestParam);

    private CompareResult executeTest(HttpRequest request, HttpResponse expect, JsonUnitComparator comparator) {
        log.debug(expect.debug(true));
        HttpResponse actual = HttpUtil.execute(request);
        log.debug(actual.debug(false));
        return comparator.compareHttpResponse(expect, actual);
    }

    private CompareResult doTest(String testcase, HttpRequest request, HttpResponse expect, JsonUnitComparator comparator) {
        CompareResult compareResult;
        try {
            log.debug("--------------------  TestCase Started  --------------------");
            log.info(testcase + " ...");

            compareResult = executeTest(request, expect, comparator);

            if (!compareResult.isSuccess()) {
                compareResult.getErrorMsg().forEach(log::error);
            }

            log.debug("--------------------  TestCase Finished  --------------------");

            log.debug("\n\n");
            Truth.assertWithMessage(testcase + "\n\t" + compareResult.error()).that(compareResult.isSuccess()).isTrue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return compareResult;
    }
}
