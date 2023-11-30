package com.example.demo.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopPojo {
    private int id;
    private String shopname;
    private BigDecimal shopprice;
    private BigDecimal shopmoey;//打折之后的价格
    private String shopurl;
    private String shopping;
    private String shopsort;
    private String username;

}
