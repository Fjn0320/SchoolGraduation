package com.example.demo.mapper;

import com.example.demo.pojo.UserLoginPojo;
import com.example.demo.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface UserMapper {

    int insertUser(UserPojo userPojo);//插入数据

    int updateUser(UserPojo userPojo);//修改数据

    int deleteUser(Integer id);//删除数据

    List<UserPojo> getPageAllUser(Integer pageNum, Integer pageSize,String username);

    int selectUserNum(String username);//查询用户条数

    Integer deleteAllUser(List list);

    List<UserPojo> list();

    void saveBatch(UserPojo list);

    void insertLogin(String username, String password);

    UserPojo selectById(Integer valueOf);


    UserPojo getUserData(String username);
}
