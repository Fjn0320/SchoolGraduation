package com.example.demo.pojo;

import lombok.Data;

@Data
public class FilePojo {
    private int id;
    private String name;
    private String type;
    private Long size;
    private String url;
    private String md5;
    private Boolean isDelete;
    private Boolean enable;


}
