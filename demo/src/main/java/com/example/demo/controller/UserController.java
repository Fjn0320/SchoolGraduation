package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.demo.common.AuthAccess;
import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import com.example.demo.pojo.UserPojo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")//给统一的接口加前缀
public class UserController {

    //怎么引入在yml中文件的路径呢？
//    @Value("${files.upload.path1}")
//    private String fileUploadPath;
    @Autowired
    private UserService userService;

    //查询所有用户  -----进行分页查询
    //RequestParam接收的是 在路径后面会有  ?pageNum=1&pageSize=10
    //limit的参数  pageNum = (pageNum - 1) * pageSize
    @GetMapping("/getPageAllUser")
    public Wrapper<Map<String,Object>> getPageAllUser(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam String username){
        pageNum = (pageNum - 1) * pageSize;
        List<UserPojo> pageUserAll = userService.getPageAllUser(pageNum,pageSize,username);

        Integer userSum = userService.selectUserNum(username);//获取用户的条数
        Map<String, Object> res = new HashMap<>();
        res.put("data",pageUserAll);
        res.put("total",userSum);
        if (pageUserAll == null){
            return WrapMapper.error();
        }
       

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,res);
    }

    @GetMapping("/getUserData/{username}")
    public Wrapper<UserPojo> getUserData(@PathVariable String username){
        return WrapMapper.ok(userService.getUserData(username));
    }


    @PostMapping("/userInsert")
    public Wrapper<UserPojo> userInsert(@RequestBody UserPojo userPojo){
        UserPojo userInsert = userService.insertUser(userPojo);
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,userInsert);
    }

    @PostMapping("/userUpdate")
    public Wrapper<UserPojo> userUpdate(@RequestBody UserPojo userPojo){
        System.out.println(userPojo);
        UserPojo userUpdate = userService.updateUser(userPojo);
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,userUpdate);
    }

    @PostMapping("/userDelete/{id}")
    public Wrapper<Integer> userDelete(@PathVariable Integer id){
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,userService.deleteUser(id));
    };

    @PostMapping("/userAllDelete")
    public Wrapper<Integer> userAllDelete(@RequestBody List<Integer> list){
        return  WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,userService.deleteAllUser(list));
    };

    //导出数据
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        // 从数据库查询出所有的数据
        List<UserPojo> list = userService.list();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "ID");
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("uniname", "名字");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("imgurl", "图像");
        writer.addHeaderAlias("ctime", "创建时间");
        writer.addHeaderAlias("token", "token");


        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }
    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<UserPojo> list = reader.readAll(UserPojo.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<UserPojo> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            UserPojo user = new UserPojo();
            user.setUsername(row.get(1).toString());
            user.setPassword(row.get(2).toString());
            user.setNickname(row.get(3).toString());
            user.setUniname(row.get(4).toString());
            user.setEmail(row.get(5).toString());
            user.setPhone(row.get(6).toString());
            user.setAddress(row.get(7).toString());
            user.setImgurl(row.get(8).toString());
            user.setCtime(Convert.toDate(row.get(9)));
//            users.add(user);
            userService.saveBatch(user);
        }
        System.out.println(list);
        return true;
    }



}
