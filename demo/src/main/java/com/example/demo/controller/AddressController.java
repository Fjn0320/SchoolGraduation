package com.example.demo.controller;


import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import com.example.demo.mapper.AddressMapper;
import com.example.demo.pojo.AddressPojo;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")//给统一的接口加前缀
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public Wrapper<Integer> add(@RequestBody AddressPojo addressPojo){
        return WrapMapper.ok(addressService.add(addressPojo));
    }
    @GetMapping("/selectAll")
    public Wrapper<List<AddressPojo>> selectAll(@RequestParam Integer userid){
        return WrapMapper.ok(addressService.selectAll(userid));
    }

    @PostMapping("/deleteAddress/{id}")
    public Wrapper<Integer> deleteAddress(@PathVariable Integer id){
        return WrapMapper.ok(addressService.deleteAddress(id));
    }

    @PostMapping("/updateAddress")
    public Wrapper<Integer> updateAddress(@RequestBody AddressPojo address){
        return WrapMapper.ok(addressService.updateAddress(address));
    }

    @GetMapping("/selectById")
    public Wrapper<AddressPojo> selectById(@RequestParam Integer id){
        return WrapMapper.ok(addressService.selectById(id));
    }
}
