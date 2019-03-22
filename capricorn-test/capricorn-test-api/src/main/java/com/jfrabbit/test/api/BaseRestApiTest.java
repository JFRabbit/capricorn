package com.jfrabbit.test.api;

import com.jfrabbit.common.rest.util.compare.Placeholder;
import com.jfrabbit.common.util.json.JsonPathUtil;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Listeners;

/**
 * 整合TestNG&Spring，需要在项目中指定一个类当做Spring的配置类，扫描包、aop等
 *
 * @author JasonZhang 2018/7/30 下午5:19
 */
@Listeners({TestNGListenerForRestApi.class})
public class BaseRestApiTest extends AbstractTestNGSpringContextTests implements ParamUtil, Placeholder {
    protected static final String ALL = "ALL";
    protected static final String SMOKE = "SMOKE";

    protected JsonPathUtil node = new JsonPathUtil();
}
