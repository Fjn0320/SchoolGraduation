package com.example.demo.pojo;

import lombok.Data;

@Data
public class ShopDingDanDTO {
    //从前台传过来的数据
    private Integer shopid;//商家id
    private String user;//下单的用户
    private String address;//用户地址
    private String phone;//下单的用户手机号
    private String comment;//备注信息
}
