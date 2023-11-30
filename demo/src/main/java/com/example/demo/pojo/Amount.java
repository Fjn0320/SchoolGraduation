package com.example.demo.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Amount {
    private BigDecimal amount;//总价
    private BigDecimal discount;//优惠后的价格
    private BigDecimal actual;//实际价格
}
