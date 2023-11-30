package com.example.demo.controller;

import com.example.demo.common.AuthAccess;
import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")//给统一的接口加前缀
public class WebController {


    @AuthAccess
    @GetMapping("/get")
    public Wrapper get(){
        return WrapMapper.ok("访问成功");
    }
}
