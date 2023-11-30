package com.example.demo.pojo;

import lombok.Data;

/**
 *
 * 接受前端登录的数据
 */
@Data
public class UserLoginPojo {
    private String username;
    private String password;
    private String token;//设置token
}
