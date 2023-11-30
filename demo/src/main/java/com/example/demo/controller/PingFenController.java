package com.example.demo.controller;


import com.example.demo.common.AuthAccess;
import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import com.example.demo.pojo.ShopPingFen;
import com.example.demo.pojo.ShopPojo;
import com.example.demo.service.PingFenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ping")//给统一的接口加前缀
public class PingFenController {

    @Autowired
    private PingFenService pingfenService;


    @GetMapping("/getPageAllPing")
    public Wrapper<Map<String,Object>> getPageAllPing(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam String shopname){
        pageNum = (pageNum - 1) * pageSize;
        List<ShopPingFen> pagePingAll = pingfenService.getPageAllPing(pageNum,pageSize,shopname);

        Integer pingSum = pingfenService.selectPingNum(shopname);//获取用户的条数
        Map<String, Object> res = new HashMap<>();
        res.put("data",pagePingAll);
        res.put("total",pingSum);
        if (pagePingAll == null){
            return WrapMapper.error();
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,res);
    }

    @GetMapping("/getPageAllPingShop")
    public Wrapper<Map<String,Object>> getPageAllPingShop(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam String goname,
                                                          @RequestParam String username ){
        pageNum = (pageNum - 1) * pageSize;
        String shopping = pingfenService.getShoping(username);//到shop获取商家的店名
        List<ShopPingFen> pagePingAll = pingfenService.getPageAllPingShop(pageNum,pageSize,goname,shopping);

        Integer pingSum = pingfenService.selectPingShopNum(goname,shopping);//获取用户的条数
        Map<String, Object> res = new HashMap<>();
        res.put("data",pagePingAll);
        res.put("total",pingSum);
        if (pagePingAll == null){
            return WrapMapper.error();
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,res);
    }



    @PostMapping("/pingDelete/{id}")
    public Wrapper<Integer> pingDelete(@PathVariable Integer id){
        return  WrapMapper.ok(pingfenService.pingDelete(id));
    };
}
