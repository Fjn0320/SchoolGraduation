package com.example.demo.service;

import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.RoleMenuMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.MenuPojo;
import com.example.demo.pojo.RoleMenuPojo;
import com.example.demo.pojo.RolePojo;
import com.example.demo.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    public List<RolePojo> getPageAllUser(Integer pageNum, Integer pageSize, String name) {
        return roleMapper.getPageAllUser(pageNum,pageSize,name);
    }
    public int selectUserNum(String name){
        return roleMapper.selectUserNum(name);
    }

    @Transactional
    public void setRoleMenu(Integer roleId, List<Integer> menuIds){
        // 先删除当前角色id所有的绑定关系
        roleMenuMapper.deleteByRoleId(roleId);

        // 再把前端传过来的菜单id数组绑定到当前的这个角色id上去
        for (Integer menuId : menuIds) {
            RoleMenuPojo roleMenu = new RoleMenuPojo();
            roleMenu.setRoleid(roleId);
            roleMenu.setMenuid(menuId);
            roleMenuMapper.insert(roleMenu);
        }

    }

    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);

    }

    public List<RolePojo> getRole() {
        return  roleMapper.getRole();
    }
}
