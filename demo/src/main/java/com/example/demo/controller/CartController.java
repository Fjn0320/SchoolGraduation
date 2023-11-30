package com.example.demo.controller;

import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import com.example.demo.pojo.Amount;
import com.example.demo.pojo.ShopCartPojo;
import com.example.demo.pojo.ShopInfoPojo;
import com.example.demo.pojo.ShopPojo;
import com.example.demo.service.CartService;
import com.example.demo.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cart")//给统一的接口加前缀
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private ShopService shopService;

    @PostMapping("/add")
    public Wrapper<Integer> add(@RequestBody ShopCartPojo shopCartPojo){
       return WrapMapper.ok(cartService.add(shopCartPojo));
    }

    @PostMapping("/deleteCart/{userid}/{businessid}")
    public Wrapper<Integer> deleteCart(@PathVariable Integer userid,@PathVariable Integer businessid){
        System.out.println(userid);
        return WrapMapper.ok(cartService.deleteCart(userid,businessid));
    }

    @GetMapping("/selectAllCart")//根据id进行查询
    public Wrapper<List<ShopCartPojo>> selectAllCart(@RequestParam int userid,@RequestParam int businessid){
        List<ShopCartPojo> cartPojoList = cartService.selectAllCart(userid,businessid);
        for (ShopCartPojo cart:cartPojoList) {
            ShopPojo shopPojo =  shopService.selectAllById(cart.getShopid());
            cart.setShopPojo(shopPojo);
            //查询出商家的信息
            ShopInfoPojo shopInfoId = shopService.getShopInfoId(cart.getShopid());
            cart.setShopInfoPojo(shopInfoId);
        }
        return WrapMapper.ok(cartPojoList);
    }


    @PostMapping("/updateCart")
    public Wrapper<Integer> updateCart(@RequestBody ShopCartPojo shopCartPojo){
        return WrapMapper.ok(cartService.updateCart(shopCartPojo));
    }

    //计算所有商品的总和
    @GetMapping("/calc")
    public Wrapper<Amount> calc(@RequestParam int userid, @RequestParam int businessid){;
        Amount calc = cartService.calc(userid, businessid);

        return WrapMapper.ok(calc);
    }
    //清除购物单，是要根据商家id和用户id

}
