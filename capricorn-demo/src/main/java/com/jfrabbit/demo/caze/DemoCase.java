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
// assign env variable
@Env(env = ApiEnv.class, value = "your_env")
// assign url mapping
@Request(mapping = "/your_url_mapping")
public class DemoCase {

    // url after assemble 组装后的url your_env/your_url_mapping/your_url/{some_dynamic_params}/foo
    @Request(url = "/your_url/{some_dynamic_params}/foo", method = HttpMethodEnum.POST)
    @ApiTestCase("test demo") // test case name
    // use CompareResult for return class，first param must extends RestRequestParam，only return null
    // 返回结果使用CompareResult，第一个参数必须继承RestRequestParam，返回null即可
    public CompareResult foo(FooRequestParam param) {
        return null;
    }
}
