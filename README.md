# Test Staging for RESTful API

# [Demo:](https://github.com/JFRabbit/capricorn/tree/dev/capricorn-demo)

### Define Spring config 定义Spring相关配置
```java
@Lazy // default Lazy type 默认全局为Lazy模式
@Configuration
@ComponentScan(basePackages = {
        "com.jfrabbit.demo"
})
@EnableAspectJAutoProxy
public class DemoApiSpringConfig {}
```

```java
// assign Spring config class 指定Spring配置类
@ContextConfiguration(classes = DemoApiSpringConfig.class)
public class DemoRestApiTest extends BaseRestApiTest {
}
```

### Define Spring aop 定义Spring aop
```java
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
```

### Define environment variables 定义环境变量
```yaml
DEMO:
  FOO:
    URL: http://xxx/xxx
```

```java
/**
 * read config yaml file from resource or relative path 
 * 相对路径读取配置文件，优先读取resources路径，如果失败，读取项目根路径或jar包同路径
 * 
 * sequence: try path one by one and use first correct
 * 依次根据路径参数数组读取，使用第一个读取成功的文件
 */
@Component
@Yml(path = {"config/config.yaml", "config.yaml"})
@Lazy(false)
public class ApiEnv {

    // inject param from config file by expression 通过表达式，注入配置文件
    @Yml("$.DEMO.FOO.URL")
    private static String your_env;

    @PostConstruct
    public void init() {
        YamlUtil.injectYamlField(this);
    }

}
```

## 定义Rest接口
```java
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
```

### 定义测试基类
```java
/**
 * extends base class, declare java bean, define constant or common function
 * 继承测试基类、声明java bean、定义常量和公共方法
 */
public class TestBase extends DemoRestApiTest {

    @Resource
    protected DemoCase demoCase;
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
```

### CI
Suggestion: package your test project, use TestNG XML to run test case, build docker image and deploy to piplines.
建议将测试项目打成jar包，使用TestNG的XML文件执行用例，制作成docker镜像并部署到流水线。