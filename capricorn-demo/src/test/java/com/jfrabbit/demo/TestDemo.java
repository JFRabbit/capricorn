package com.jfrabbit.demo;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.jfrabbit.common.rest.HttpResponse;
import com.jfrabbit.common.rest.util.compare.JsonUnitComparator;
import net.javacrumbs.jsonunit.core.Option;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author JasonZhang 2019-03-22 17:55
 */
public class TestDemo extends TestBase {

    @Test(dataProvider = "provider", groups = {ALL})
    public void test_demo(FooRequestParam param) {
        demoCase.foo(param);
    }

    @DataProvider
    public Object[][] provider() {
        return new Object[][]{
                {
                        new FooRequestParam("username", "password")
                                // http://xxx/xxx?url_param=foo
                                .params(new ImmutableMap.Builder<String, String>()
                                        .put("url_param", "foo")
                                        .build()
                                )
                                // http://xxx/xxx/{some_dynamic_params}/xxx
                                .dynamic(new ImmutableMap.Builder<String, String>()
                                        .put("some_dynamic_params", "dynamic")
                                        .build()
                                )
                                // url payload, use map or object
                                .payload(ImmutableBiMap.builder()
                                        .put("username", "your username")
                                        .put("password", "your password")
                                        .build()
                                )
                                // upload file with Multipart
                                .upload("relative file path in resource", "key")
                                // assert response code is 200 and response payload is FooResponseCode instance or map
                                // 校验响应码为200，响应的body体与实体或map一致
                                .response(new HttpResponse(
                                                200,
                                                new FooResponseCode(
                                                        0,
                                                        // use JsonAssert expression
                                                        JSON_ANY_STRING,
                                                        "login success",
                                                        "success",
                                                        "")
                                        )
                                )
                                // set JsonAssert rule 可以通过JsonAssert指定校验规则
                                .comparator(new JsonUnitComparator(Option.IGNORING_EXTRA_ARRAY_ITEMS))
                }
        };
    }
}
