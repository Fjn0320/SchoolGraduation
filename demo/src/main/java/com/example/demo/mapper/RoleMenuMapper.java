package com.example.demo.mapper;


import com.example.demo.pojo.RoleMenuPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMenuMapper {

    int deleteByRoleId(Integer roleId);

    void insert(RoleMenuPojo roleMenu);

    List<Integer> selectByRoleId(Integer roleId);
}
