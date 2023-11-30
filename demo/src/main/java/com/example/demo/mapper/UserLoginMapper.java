package com.example.demo.mapper;

import com.example.demo.pojo.UserLoginPojo;
import com.example.demo.pojo.UserPojo;

public interface UserLoginMapper {

    UserPojo userSelectLogin(String username);

    int userRegister(UserPojo userPojo);

    UserPojo userSelectRegister(String username);
}
