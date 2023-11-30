package com.example.demo.service;

import com.example.demo.mapper.CartMapper;
import com.example.demo.pojo.Amount;
import com.example.demo.pojo.ShopCartPojo;
import com.example.demo.pojo.ShopInfoPojo;
import com.example.demo.pojo.ShopPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private ShopService shopService;

    public int add(ShopCartPojo shopCartPojo) {
        return cartMapper.add(shopCartPojo);
    }

    public List<ShopCartPojo> selectAllCart(int userid,int businessid) {
//        List<ShopCartPojo> cartPojoList = cartMapper.selectAllCart(userid,businessid);
//        for (ShopCartPojo cart:cartPojoList) {
//            ShopPojo shopPojo =  shopService.selectAllById(cart.getShopid());
//            cart.setShopPojo(shopPojo);
//            //查询出商家的信息
//            ShopInfoPojo shopInfoId = shopService.getShopInfoId(cart.getShopid());
//            cart.setShopInfoPojo(shopInfoId);
//        }
        return cartMapper.selectAllCart(userid,businessid);
    }

    public int updateCart(ShopCartPojo shopCartPojo) {
        return cartMapper.updateCart(shopCartPojo);
    }


    public Integer deleteCart(Integer userid, Integer businessid) {
        System.out.println(userid);
        System.out.println(businessid);
        return cartMapper.deleteCart(userid,businessid);
    }

    public Amount calc(int userid, int businessid) {
        //查出当前用户所在商家的中的购物车
        List<ShopCartPojo> shopCartPojos = this.selectAllCart(userid, businessid);
        for (ShopCartPojo cart:shopCartPojos) {
            ShopPojo shopPojo =  shopService.selectAllById(cart.getShopid());
            cart.setShopPojo(shopPojo);
        }
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal actual = BigDecimal.ZERO;
        for (ShopCartPojo shopCartPojo:
                shopCartPojos) {
            ShopPojo s1 = shopCartPojo.getShopPojo();
            if (s1 != null){
                BigDecimal price =s1.getShopprice();
                BigDecimal actualPrice =s1.getShopmoey();
                amount = amount.add(price.multiply(BigDecimal.valueOf(shopCartPojo.getNum())));//实际价格
                actual = actual.add(actualPrice.multiply(BigDecimal.valueOf(shopCartPojo.getNum())));//打折之后的价格
            }
        }
        Amount amount1 = new Amount();
        amount1.setAmount(amount);
        amount1.setActual(actual);
        amount1.setDiscount(amount.subtract(actual));
        return amount1;
    }
}
