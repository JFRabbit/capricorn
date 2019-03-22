# REST API TEST 脚手架

# Cool Demo

## Maven 依赖 TODO

## 定义环境变量
```java
@Component
@Yml(path = {"config/config.yaml", "config.yaml"})
@Lazy(false)
public class ApiEnv {

    @Yml("$.DEMO.FOO.URL")
    private static String your_env_param;

    @PostConstruct
    public void init() {
        YamlUtil.injectYamlField(this);
    }
}
```

## 定义Rest接口
```java
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

```

## 定义测试类
```java
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
                                // check response code is 200 and response payload is FooResponseCode instance
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
                                // set JsonAssert rule
                                .comparator(new JsonUnitComparator(Option.IGNORING_EXTRA_ARRAY_ITEMS))
                }
        };
    }
}
```

## 定义Spring相关配置

## 定义Rest接口

## 定义aop

## 定义测试基类
