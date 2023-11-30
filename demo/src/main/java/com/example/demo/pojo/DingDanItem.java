package com.example.demo.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DingDanItem {
    //显示在订单列表的信息
    private int id;
    private String dingdanid;//订单的编号
    private int shopid;//商家id
    private int userid;//用户id
    private int sum;//所订单的数量
    private String shoping;//商家名字
    private String shopname;//订单名称
    private String shopurl;//订单图片
    private BigDecimal shopmoney;//订单金额
}
