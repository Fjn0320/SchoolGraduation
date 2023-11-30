package com.example.demo.mapper;

import com.example.demo.pojo.RolePojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<RolePojo> getPageAllUser(Integer pageNum, Integer pageSize, String name);

    int selectUserNum(String name);//查询用户条数

    int setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<RolePojo> getRole();

    Integer selectRoleFlag(String flag);
}
