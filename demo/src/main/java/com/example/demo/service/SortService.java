package com.example.demo.service;

import com.example.demo.mapper.PingFenMapper;
import com.example.demo.mapper.SortMapper;
import com.example.demo.pojo.ShopCartPojo;
import com.example.demo.pojo.ShopInfoPojo;
import com.example.demo.pojo.ShopPingFen;
import com.example.demo.pojo.ShopSortPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortService {

    @Autowired
    private SortMapper sortMapper;

    public  int sortDelete(Integer id) {
        return sortMapper.sortDelete(id);
    }

    public List<ShopSortPojo> getPageAllSort(Integer pageNum, Integer pageSize, String shoping) {
        return sortMapper.getPageAllSort(pageNum,pageSize,shoping);
    }

    public Integer selectSortNum(String shoping) {
        return sortMapper.selectSortNum(shoping);
    }

    public List<ShopInfoPojo> getPageAllInfo(Integer pageNum, Integer pageSize, String shoping) {
        return sortMapper.getPageAllInfo(pageNum,pageSize,shoping);
    }

    public Integer selectInfoNum(String shoping) {
        return sortMapper.selectInfoNum(shoping);
    }
}
