package com.example.demo.controller;


import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import com.example.demo.pojo.MenuPojo;
import com.example.demo.pojo.RolePojo;
import com.example.demo.pojo.UserPojo;
import com.example.demo.service.MenuService;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")//给统一的接口加前缀
public class MenuController {


    @Autowired
    private MenuService menuService;

//
//    @GetMapping("/getPageAllMenu")
//    public Wrapper<Map<String,Object>> getPageAllUser(@RequestParam Integer pageNum,
//                                                      @RequestParam Integer pageSize,
//                                                      @RequestParam String name){
//        pageNum = (pageNum - 1) * pageSize;
//        List<MenuPojo> pageUserAll = menuService.getPageAllMenu(pageNum,pageSize,name);
//
//        Integer userSum = menuService.selectMenuNum(name);//获取用户的条数
//        Map<String, Object> res = new HashMap<>();
//        res.put("data",pageUserAll);
//        res.put("total",userSum);
//        if (pageUserAll == null){
//            return WrapMapper.error();
//        }
//        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,res);
//    }

    @GetMapping("/getAllMenu")
    public Wrapper<List<MenuPojo>> getAllMenu(){

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, menuService.findMenus());
    }
    @PostMapping("/menuInsert")
    public Wrapper<MenuPojo> menuInsert(@RequestBody MenuPojo menuPojo){
        System.out.println(menuPojo);
        MenuPojo userInsert = menuService.insertMenu(menuPojo);
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,userInsert);
    }

    @PostMapping("/menuUpdate")
    public Wrapper<MenuPojo> menuUpdate(@RequestBody MenuPojo menuPojo){
        System.out.println(menuPojo);
        MenuPojo userInsert = menuService.updateMenu(menuPojo);
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,userInsert);
    }

}
