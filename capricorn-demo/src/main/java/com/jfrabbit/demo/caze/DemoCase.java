package com.jfrabbit.demo.caze;

import com.jfrabbit.common.rest.HttpMethodEnum;
import com.jfrabbit.common.rest.util.compare.CompareResult;
import com.jfrabbit.demo.ApiEnv;
import com.jfrabbit.demo.FooRequestParam;
import com.jfrabbit.test.api.annotation.ApiTestCase;
import com.jfrabbit.test.api.annotation.Env;
import com.jfrabbit.test.api.annotation.Request;
import org.springframework.stereotype.Component;

/**
 * @author JasonZhang 2018/6/9 下午1:48
 */
@Component
@Env(env = ApiEnv.class, value = "your_env_param")
@Request(mapping = "your url mapping")
public class DemoCase {

    @Request(url = "/your_url/{some_dynamic_params}/foo", method = HttpMethodEnum.POST)
    @ApiTestCase("test demo")
    public CompareResult foo(FooRequestParam param) {
        return null;
    }
}
