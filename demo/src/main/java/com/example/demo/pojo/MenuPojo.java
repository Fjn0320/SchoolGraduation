package com.example.demo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class MenuPojo {
    private Integer id;
    private Integer pid;
    private String name;
    private String path;
    private String description;
    private List<MenuPojo> children;
    private String pagepath;

}
