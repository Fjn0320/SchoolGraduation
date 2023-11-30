package com.example.demo.mapper;

import com.example.demo.pojo.ShopCartPojo;
import com.example.demo.pojo.ShopInfoPojo;
import com.example.demo.pojo.ShopPingFen;
import com.example.demo.pojo.ShopSortPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SortMapper {

    List<ShopSortPojo> getPageAllSort(Integer pageNum, Integer pageSize, String shoping);

    Integer selectSortNum(String shoping);

    int sortDelete(Integer id);

    List<ShopInfoPojo> getPageAllInfo(Integer pageNum, Integer pageSize, String shoping);

    Integer selectInfoNum(String shoping);
}
