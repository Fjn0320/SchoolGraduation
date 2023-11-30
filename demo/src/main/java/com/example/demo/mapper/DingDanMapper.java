package com.example.demo.mapper;

import com.example.demo.pojo.ShopDingDan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DingDanMapper {

    List<ShopDingDan> getPageAllDing(Integer pageNum, Integer pageSize, String shopname);

    Integer selectDingNum(String shopname);

    int dingDelete(Integer id);

    Integer getShopInfoId(String username);

    List<ShopDingDan> getPageAllDingShop(Integer pageNum, Integer pageSize, String shopname, Integer id);

    Integer selectDingNumShop(String shopname, Integer id);
}
