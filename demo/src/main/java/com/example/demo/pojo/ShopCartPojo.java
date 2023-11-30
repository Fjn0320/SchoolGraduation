package com.example.demo.pojo;

import lombok.Data;

@Data
public class ShopCartPojo {
    private int id;
    private int shopid;
    private int num;
    private int userid;
    private int businessid;
    private ShopPojo shopPojo;//菜品信息
    private ShopInfoPojo shopInfoPojo;//商家信息
}
