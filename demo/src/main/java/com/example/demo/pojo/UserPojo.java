package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class UserPojo {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String uniname;
    private String email;
    private String phone;
    private String address;
    private String imgurl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date ctime;
    private String token;
    List<MenuPojo> menu;
}
