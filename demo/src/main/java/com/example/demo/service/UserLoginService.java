package com.example.demo.service;


import com.example.demo.common.Wrapper;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserLoginMapper;
import com.example.demo.pojo.UserPojo;
import com.example.demo.until.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    @Autowired
    private UserLoginMapper userLoginMapper;

    public UserPojo userLogin(UserPojo userPojo) {
        //查询数据库的用户信息
        UserPojo dbuserLoginPojo = userLoginMapper.userSelectLogin(userPojo.getUsername());
        if (dbuserLoginPojo == null){
            //Wrapper.ILLEGAL_ARGUMENT_CODE_:参数非法
            throw new ServiceException(Wrapper.ILLEGAL_ARGUMENT_CODE_,"没有该用户");
        }
        if (!userPojo.getPassword().equals(dbuserLoginPojo.getPassword())) {
            throw new ServiceException(Wrapper.ILLEGAL_ARGUMENT_CODE_,"用户名或密码错误");
        }
            //生成token
            String token = TokenUtils.genToken(dbuserLoginPojo.getId().toString(),dbuserLoginPojo.getPassword());
            System.out.println("token::------------------" +
                    ""+token);
            dbuserLoginPojo.setToken(token);
            return dbuserLoginPojo;
    }

    public UserPojo userRegister(UserPojo userPojo) {
        UserPojo dbuserPojo = userLoginMapper.userSelectRegister(userPojo.getUsername());
        if (dbuserPojo != null){
            //Wrapper.USER_CODE=999说明账户已注册
            throw new ServiceException(Wrapper.USER_CODE,"该账户已注册");
        }
        if(userPojo.getNickname().equals("管理员") ){
            userPojo.setNickname("ROLE_ADMIN");
        }else if (userPojo.getNickname().equals("商家")){
            userPojo.setNickname("ROLE_SHOP");
        }else {
            userPojo.setNickname("ROLE_USER");
        }
        userPojo.setUsername(userPojo.getUsername());
        userLoginMapper.userRegister(userPojo);
        return userPojo;
    }
}
