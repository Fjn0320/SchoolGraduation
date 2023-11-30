package com.example.demo.service;

import com.example.demo.mapper.DingDanMapper;
import com.example.demo.pojo.ShopDingDan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DingDanService {

    @Autowired
    private DingDanMapper dingDanMapper;


    public List<ShopDingDan> getPageAllDing(Integer pageNum, Integer pageSize, String shopname) {
        return dingDanMapper.getPageAllDing(pageNum,pageSize,shopname);
    }

    public Integer selectDingNum(String shopname) {
        return dingDanMapper.selectDingNum(shopname);
    }

    public int dingDelete(Integer id) {
        return dingDanMapper.dingDelete(id);
    }

    public Integer getShopInfoId(String username) {
        return dingDanMapper.getShopInfoId(username);
    }

    public List<ShopDingDan> getPageAllDingShop(Integer pageNum, Integer pageSize, String shopname, Integer id) {
        return dingDanMapper.getPageAllDingShop(pageNum,pageSize,shopname,id);
    }

    public Integer selectDingNumShop(String shopname, Integer id) {
        return dingDanMapper.selectDingNumShop(shopname,id);
    }
}
