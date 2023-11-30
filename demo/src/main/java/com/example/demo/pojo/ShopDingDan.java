package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShopDingDan {
    private int id;
    private String shopid;
    private String shopname;
    private int shoping;//商家id
    private String goadress;
    private String gophone;
    private String goname;
    private BigDecimal shopmoney;
    private String shopcomment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date shoptime;
}
