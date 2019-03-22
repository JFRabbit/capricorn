package com.jfrabbit.demo;

import com.jfrabbit.common.util.YamlUtil;
import com.jfrabbit.common.util.Yml;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author JasonZhang 2018/6/9 下午6:13
 */
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