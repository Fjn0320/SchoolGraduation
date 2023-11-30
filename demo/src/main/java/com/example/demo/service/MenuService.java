package com.example.demo.service;

import com.example.demo.mapper.MenuMapper;
import com.example.demo.pojo.MenuPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;



    public List<MenuPojo> getPageAllMenu(Integer pageNum, Integer pageSize, String name) {
        return menuMapper.getPageAllMenu(pageNum,pageSize,name);
    }
    public int selectMenuNum(String name){
        return menuMapper.selectMenuNum(name);
    }

    public List<MenuPojo> getAllMenu() {
        return menuMapper.getAllMenu();
    }

    public MenuPojo insertMenu(MenuPojo menuPojo) {
        menuMapper.insertMenu(menuPojo);
        return menuPojo;
    }

    public List<MenuPojo> findMenus() {
        //查询所有数据
        List<MenuPojo> userAll = getAllMenu();
        //找出pid为空的一级菜单
        List<MenuPojo> collect = userAll.stream().filter(menuPojo -> menuPojo.getPid() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (MenuPojo menuPojo : collect){
            //筛选所有数据中pid等于父级的id
            menuPojo.setChildren(userAll.stream().filter(menu -> menuPojo.getId().equals(menu.getPid())).collect(Collectors.toList()));
        }
        return collect;
    }

    public MenuPojo updateMenu(MenuPojo menuPojo) {
        return menuMapper.updateMenu(menuPojo);
    }
}
