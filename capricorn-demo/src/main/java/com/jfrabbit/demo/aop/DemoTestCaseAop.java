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

    // testcase package 测试用例的package
    private static final String CASE_PACKAGE = "com.jfrabbit.demo.caze";

    // define around rule 定义环绕规则
    @Around(AOP_RULE_PREFIX + CASE_PACKAGE + "..*.*(com.jfrabbit.demo.FooRequestParam))")
    public Object testcaseAspect(ProceedingJoinPoint p) throws Throwable {
        return super.testcaseAspect(p);
    }

    // override login function 重写登录方法
    @Override
    public void setLoginToken(HttpRequest request, RestRequestParam requestParam) {
        // cast son class to get login param 可将参数强转为继承类，通过get方法获取登录参数
        FooRequestParam param = (FooRequestParam) requestParam;

        if (param == null) {
            throw new RuntimeException();
        } else if (param.getUsername() != null && param.getPassword() != null) {
            // TODO call login interface to assemble header 调用登录接口后，组装header
        } else {
            request.header(new HashMap<>());
        }
    }
}
