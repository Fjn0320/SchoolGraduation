package com.example.demo.controller;


import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import com.example.demo.pojo.ShopDingDan;
import com.example.demo.pojo.ShopPingFen;
import com.example.demo.service.DingDanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ding")//给统一的接口加前缀
public class DingDanController {
    @Autowired
    private DingDanService dingDanService;



    @GetMapping("/getPageAllDing")
    public Wrapper<Map<String,Object>> getPageAllDing(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam String shopname){
        pageNum = (pageNum - 1) * pageSize;
        List<ShopDingDan> pagePingAll = dingDanService.getPageAllDing(pageNum,pageSize,shopname);

        Integer pingSum = dingDanService.selectDingNum(shopname);//获取用户的条数
        Map<String, Object> res = new HashMap<>();
        res.put("data",pagePingAll);
        res.put("total",pingSum);
        if (pagePingAll == null){
            return WrapMapper.error();
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,res);
    }
    @PostMapping("/dingDelete/{id}")
    public Wrapper<Integer> dingDelete(@PathVariable Integer id){
        return  WrapMapper.ok(dingDanService.dingDelete(id));
    }

    @GetMapping("/getPageAllDingShop")
    public Wrapper<Map<String,Object>> getPageAllDingShop(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam String shopname,
                                                          @RequestParam String username){
        pageNum = (pageNum - 1) * pageSize;
        Integer id = dingDanService.getShopInfoId(username);
        List<ShopDingDan> pagePingAll = dingDanService.getPageAllDingShop(pageNum,pageSize,shopname,id);

        Integer pingSum = dingDanService.selectDingNumShop(shopname,id);//获取用户的条数
        Map<String, Object> res = new HashMap<>();
        res.put("data",pagePingAll);
        res.put("total",pingSum);
        if (pagePingAll == null){
            return WrapMapper.error();
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,res);
    }

}
