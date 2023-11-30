package com.example.demo.mapper;

import com.example.demo.pojo.Amount;
import com.example.demo.pojo.ShopCartPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    int add(ShopCartPojo shopCartPojo);

    List<ShopCartPojo> selectAllCart(int userid, int businessid);

    int updateCart(ShopCartPojo shopCartPojo);


    Integer deleteCart(Integer userid, Integer businessid);
}
