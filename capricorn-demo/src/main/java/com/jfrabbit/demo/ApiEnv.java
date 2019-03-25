package com.jfrabbit.demo;

import com.jfrabbit.common.util.YamlUtil;
import com.jfrabbit.common.util.Yml;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * read config yaml file from resource or relative path
 * 相对路径读取配置文件，优先读取resources路径，如果失败，读取项目根路径或jar包同路径
 *
 * sequence: try path one by one and use first correct
 * 依次根据路径参数数组读取，使用第一个读取成功的文件
 *
 * @author JasonZhang 2018/6/9 下午6:13
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