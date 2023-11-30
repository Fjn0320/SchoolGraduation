package com.example.demo.controller;


import com.example.demo.common.AuthAccess;
import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import com.example.demo.pojo.ShopCartPojo;
import com.example.demo.pojo.ShopInfoPojo;
import com.example.demo.pojo.ShopPingFen;
import com.example.demo.pojo.ShopSortPojo;
import com.example.demo.service.PingFenService;
import com.example.demo.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sort")//给统一的接口加前缀
public class SortController {

    @Autowired
    private SortService sortService;


    @GetMapping("/getPageAllSort")
    public Wrapper<Map<String,Object>> getPageAllSort(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam String shoping){
        pageNum = (pageNum - 1) * pageSize;
        List<ShopSortPojo> pagePingAll = sortService.getPageAllSort(pageNum,pageSize,shoping);

        Integer pingSum = sortService.selectSortNum(shoping);//获取用户的条数
        Map<String, Object> res = new HashMap<>();
        res.put("data",pagePingAll);
        res.put("total",pingSum);
        if (pagePingAll == null){
            return WrapMapper.error();
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,res);
    }
    @GetMapping("/getPageAllInfo")
    public Wrapper<Map<String,Object>> getPageAllInfo(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam String shoping){
        pageNum = (pageNum - 1) * pageSize;
        List<ShopInfoPojo> pagePingAll = sortService.getPageAllInfo(pageNum,pageSize,shoping);

        Integer pingSum = sortService.selectInfoNum(shoping);//获取用户的条数
        Map<String, Object> res = new HashMap<>();
        res.put("data",pagePingAll);
        res.put("total",pingSum);
        if (pagePingAll == null){
            return WrapMapper.error();
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,res);
    }


    @PostMapping("/sortDelete/{id}")
    public Wrapper<Integer> sortDelete(@PathVariable Integer id){
        return  WrapMapper.ok(sortService.sortDelete(id));
    };
}
