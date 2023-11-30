package com.example.demo.service;

import com.example.demo.mapper.PingFenMapper;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.pojo.ShopPingFen;
import com.example.demo.pojo.ShopPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PingFenService {

    @Autowired
    private PingFenMapper pingFenMapper;

    public  int pingDelete(Integer id) {
        return pingFenMapper.pingDelete(id);
    }

    public List<ShopPingFen> getPageAllPing(Integer pageNum, Integer pageSize,String shopname) {
        return pingFenMapper.getPageAllPing(pageNum,pageSize,shopname);
    }

    public List<ShopPingFen> getPageAllPingShop(Integer pageNum, Integer pageSize,String goname,String shopping) {
        return pingFenMapper.getPageAllPingShop(pageNum,pageSize,goname,shopping);
    }

    public Integer selectPingNum(String shopname) {
        return pingFenMapper.selectPingNum(shopname);
    }

    public Integer selectPingShopNum(String goname,String shopping) {
        return pingFenMapper.selectPingShopNum(goname,shopping);
    }

    public String getShoping(String username) {
        return pingFenMapper.getShoping(username);
    }
}
