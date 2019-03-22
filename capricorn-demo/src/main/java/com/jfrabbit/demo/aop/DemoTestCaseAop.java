package com.jfrabbit.demo.aop;

import com.jfrabbit.common.rest.HttpRequest;
import com.jfrabbit.demo.FooRequestParam;
import com.jfrabbit.test.api.BaseRestApiTestCaseAop;
import com.jfrabbit.test.api.RestRequestParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author JasonZhang 2018/6/9 下午7:44
 */
@Component
@Aspect
public class DemoTestCaseAop extends BaseRestApiTestCaseAop {

    // 存储用例的package
    private static final String CASE_PACKAGE = "com.jfrabbit.demo.caze";

    // 集成RestRequestParam的子类
    @Around(AOP_RULE_PREFIX + CASE_PACKAGE + "..*.*(com.jfrabbit.demo.FooRequestParam))")
    public Object testcaseAspect(ProceedingJoinPoint p) throws Throwable {
        return super.testcaseAspect(p);
    }

    @Override
    public void setLoginToken(HttpRequest request, RestRequestParam requestParam) {
        // 可将参数强转为继承类，通过get方法获取登录参数
        FooRequestParam param = (FooRequestParam) requestParam;

        if (param == null) {
            throw new RuntimeException();
        } else if (param.getUsername() != null && param.getPassword() != null) {
            // 调用登录接口后，组装header
        } else {
            request.header(new HashMap<>());
        }
    }
}
