package com.example.demo.controller;


import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import com.example.demo.pojo.RolePojo;
import com.example.demo.pojo.UserPojo;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")//给统一的接口加前缀
public class RoleController {


    @Autowired
    private RoleService roleService;


    @GetMapping("/getPageAllUser")
    public Wrapper<Map<String,Object>> getPageAllUser(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam String name){
        pageNum = (pageNum - 1) * pageSize;
        List<RolePojo> pageUserAll = roleService.getPageAllUser(pageNum,pageSize,name);

        Integer userSum = roleService.selectUserNum(name);//获取用户的条数
        Map<String, Object> res = new HashMap<>();
        res.put("data",pageUserAll);
        res.put("total",userSum);
        if (pageUserAll == null){
            return WrapMapper.error();
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,res);
    }

    /**
     * 绑定角色和菜单的关系
     * @param roleId 角色id
     * @param menuIds 菜单id数组
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Wrapper roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId, menuIds);
        return WrapMapper.ok();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Wrapper getRoleMenu(@PathVariable Integer roleId) {
        return WrapMapper.ok( roleService.getRoleMenu(roleId));
    }
    @GetMapping("/getRole")
    public List<RolePojo> getRole(){
        return roleService.getRole();
    }

}
