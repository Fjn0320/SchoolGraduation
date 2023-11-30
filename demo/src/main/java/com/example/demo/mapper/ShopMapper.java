package com.example.demo.mapper;

import com.example.demo.pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopMapper {

    List<ShopPojo> getPageAllShop(Integer pageNum, Integer pageSize, String username,String shopname);

    Integer selectShopNum(String username,String shopname);

    int insertShop(ShopPojo shopPojo);

    int updateShop(ShopPojo shopPojo);

    int deleteShop(Integer id);

    List<ShopInfoPojo> getShopInfo();

    String selectById(Integer id);

    List<ShopPojo> getShopMenu(String username);

    ShopInfoPojo getShopInfoId(Integer id);

    List<ShopSortPojo> getShopSort(String username);

    List<ShopPojo> getAllMenu(String shopsort, String username);

    ShopSortPojo getShopSortId(Integer id);

    ShopPojo selectAllById(int shopid);

    int addShopDingDan(ShopDingDan shopDingDan);

    int addDingDanItem(DingDanItem dingDanItem);

    List<DingDanItem> getDingItem(Integer userid);

    DingDanItem getById(int id);

    int addPingFen(ShopPingFen shopPingFen);

    UserPojo selectCartById(int id);
}
