package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.example.demo.common.AuthAccess;
import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import com.example.demo.mapper.MenuMapper;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.pojo.MenuPojo;
import com.example.demo.pojo.UserLoginPojo;
import com.example.demo.pojo.UserPojo;
import com.example.demo.service.MenuService;
import com.example.demo.service.UserLoginService;
import com.example.demo.service.UserService;
import com.example.demo.until.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/login")//给统一的接口加前缀
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuService menuService;

    @PostMapping("/login")
    public Wrapper<UserPojo> userLogin(@RequestBody UserPojo userPojo){
        String username = userPojo.getUsername();
        String password = userPojo.getPassword();
        System.out.println(userPojo);
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return WrapMapper.error(Wrapper.ILLEGAL_ARGUMENT_CODE_, Wrapper.ILLEGAL_ARGUMENT_MESSAGE);
        }
        UserPojo dbuserLogin = userLoginService.userLogin(userPojo);
        //生成token
        String token = TokenUtils.genToken(dbuserLogin.getId().toString(), password);
        dbuserLogin.setToken(token);

        String nickName = dbuserLogin.getNickname();//获取他的唯一标识  ROLE_ADMIN

        //设置用户的菜单列表
        List<MenuPojo> roleMenu = getRoleMenu(nickName);
        dbuserLogin.setMenu(roleMenu);
        return WrapMapper.ok(dbuserLogin);
    }


    @PostMapping("/register")
    public Wrapper<UserPojo> userRegister(@RequestBody UserPojo userPojo){
        System.out.println(userPojo);
        if (StrUtil.isBlank(userPojo.getUsername()) || StrUtil.isBlank(userPojo.getPassword())) {
            return WrapMapper.error("数据输入不合法");
        }
        userPojo = userLoginService.userRegister(userPojo);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,userPojo);
    }

    private List<MenuPojo> getRoleMenu(String nickName){
        Integer roid = roleMapper.selectRoleFlag(nickName); //获取到权限的id
        //当前所有id的集合
        List<Integer> menuId = roleMenuMapper.selectByRoleId(roid);//拿到菜单id
        List<MenuPojo> menus = menuService.findMenus();//查到所有菜单

        List<MenuPojo> newMenus = new ArrayList<>();
        //筛选当前用户的菜单
        for (MenuPojo menu : menus){
            if (menuId.contains(menu.getId())){
                newMenus.add(menu);
            }
            List<MenuPojo> children = menu.getChildren();
            children.removeIf(child -> !menuId.contains(child.getId()));

        }
        return newMenus;
    }
}
