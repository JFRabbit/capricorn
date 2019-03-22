package com.jfrabbit.demo;

import com.jfrabbit.test.api.RestRequestParam;
import lombok.*;

/**
 * @author JasonZhang 2018/9/28 下午3:59
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FooRequestParam extends RestRequestParam {
    // 继承类中添加登录相关的属性，如用户名、密码，通过构造器传递数据

    private String username;
    private String password;
}
