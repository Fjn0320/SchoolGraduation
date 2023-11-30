package com.example.demo.controller;

import com.example.demo.common.AuthAccess;
import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import com.example.demo.pojo.*;
import com.example.demo.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")//给统一的接口加前缀
public class ShopController {

    @Autowired
    private ShopService shopService;

    //查询订单条目
    @GetMapping("/getDingItem")
    public Wrapper<List<DingDanItem>> getDingItem(@RequestParam Integer userid){
        return WrapMapper.ok(shopService.getDingItem(userid)) ;
    }

    //提交订单的数据
    @PostMapping("/addDingDTO")
    public Wrapper<ShopDingDanDTO> addDingDan(@RequestBody ShopDingDanDTO shopDingDanDTO){
      return   WrapMapper.ok(shopService.addDingDan(shopDingDanDTO));
    }


    @GetMapping("/getPageAllShop")
    public Wrapper<Map<String,Object>> getPageAllShop(@RequestParam Integer pageNum,
                                                      @RequestParam Integer pageSize,
                                                      @RequestParam String username,
                                                      @RequestParam String shopname){
        pageNum = (pageNum - 1) * pageSize;
        List<ShopPojo> pageUserAll = shopService.getPageAllShop(pageNum,pageSize,username,shopname);

        Integer userSum = shopService.selectShopNum(username,shopname);//获取用户的条数
        Map<String, Object> res = new HashMap<>();
        res.put("data",pageUserAll);
        res.put("total",userSum);
        if (pageUserAll == null){
            return WrapMapper.error();
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE,res);
    }


    @PostMapping("/insertShop")
    public Wrapper<ShopPojo> insertShop(@RequestBody ShopPojo shopPojo){
        ShopPojo shopInsert = shopService.insertShop(shopPojo);
        return  WrapMapper.ok(shopInsert);
    }


    @PostMapping("/shopUpdate")
    public Wrapper<ShopPojo> shopUpdate(@RequestBody ShopPojo shopPojo){
        System.out.println(shopPojo);
        ShopPojo shopUpdate = shopService.updateShop(shopPojo);
        return  WrapMapper.ok(shopUpdate);
    }


    @PostMapping("/shopDelete/{id}")
    public Wrapper<Integer> shopDelete(@PathVariable Integer id){
        return  WrapMapper.ok(shopService.deleteShop(id));
    };

    @GetMapping("/getShopInfo")
    public Wrapper<List<ShopInfoPojo>> getShopInfo(){
        return WrapMapper.ok(shopService.getShopInfo());
    }

    //根据查询id找到用户名
    @GetMapping("/selectById/{id}")
    public Wrapper<List<ShopPojo>> selectById(@PathVariable Integer id){
        //根据id查询到店主名，然后根据店主名查到相应的店铺里面的菜单信息
        String username = shopService.selectById(id);
        List<ShopPojo> shopPojoList = shopService.getShopMenu(username);
        return  WrapMapper.ok(shopPojoList);
    };

    @GetMapping("/getShopInfoId/{id}")
    public Wrapper<ShopInfoPojo> getShopInfoId(@PathVariable Integer id){
        return  WrapMapper.ok(shopService.getShopInfoId(id));
    };

    @GetMapping("/getShopSort/{username}")
    public Wrapper<List<ShopSortPojo>> getShopSort(@PathVariable String username){
        return  WrapMapper.ok(shopService.getShopSort(username));
    }

    @GetMapping("/getAllMenu")
    public Wrapper<List<ShopPojo>> getAllMenu(@RequestParam Integer id){
        //根据id查询sys_shopsort中的shopsot和username，然后再根据这两个值去查询sys_shop中所相关的值
        ShopSortPojo shopSort = shopService.getShopSortId(id);
        String shopsort = shopSort.getShopsot();
        String username = shopSort.getUsername();

        return WrapMapper.ok(shopService.getAllMenu(shopsort,username));
    }

    @PostMapping("/addPingFen")
    public Wrapper<Integer> addPingFen(@RequestParam Integer id,
                                       @RequestParam String comment){
        System.out.println(id);
        System.out.println(comment);
        return  WrapMapper.ok(shopService.addPingFen(id,comment));
    }


}
