package com.example.demo.service;


import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.UserLoginPojo;
import com.example.demo.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public UserPojo insertUser(UserPojo userPojo) {
        userMapper.insertUser(userPojo);
//        userMapper.insertLogin(userPojo.getUsername(),userPojo.getPassword());
        return userPojo;
    }
    public UserPojo updateUser(UserPojo userPojo) {
        userMapper.updateUser(userPojo);
        return userPojo;
    }

    public Integer deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    public List<UserPojo> getPageAllUser(Integer pageNum, Integer pageSize, String username) {
        return userMapper.getPageAllUser(pageNum,pageSize,username);
    }

    public int selectUserNum(String username){
        return userMapper.selectUserNum(username);
    }

    public Integer deleteAllUser(List list) {
        return userMapper.deleteAllUser(list);
    }

    public List<UserPojo> list() {
        return userMapper.list();
    }

    public void saveBatch(UserPojo list) {
        userMapper.saveBatch(list);
    }

    public UserPojo selectById(Integer valueOf) {
        return userMapper.selectById(valueOf);
    }

    public UserPojo getUserData(String username) {
        return  userMapper.getUserData(username);
    }


}
