package com.example.demo.mapper;

import com.example.demo.pojo.ShopPingFen;
import com.example.demo.pojo.ShopPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PingFenMapper {

    List<ShopPingFen> getPageAllPing(Integer pageNum, Integer pageSize,String shopname);
    List<ShopPingFen> getPageAllPingShop(Integer pageNum, Integer pageSize,String goname,String shopping);

    Integer selectPingNum(String shopname);
    Integer selectPingShopNum(String goname,String shopping);

    int pingDelete(Integer id);

    String getShoping(String username);
}
