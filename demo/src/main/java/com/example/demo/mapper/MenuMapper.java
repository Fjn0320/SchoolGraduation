package com.example.demo.mapper;

import com.example.demo.pojo.MenuPojo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuPojo> getPageAllMenu(Integer pageNum, Integer pageSize, String name);

    int selectMenuNum(String name);//查询用户条数

    List<MenuPojo> getAllMenu();

    int insertMenu(MenuPojo menuPojo);

    MenuPojo updateMenu(MenuPojo menuPojo);
}
