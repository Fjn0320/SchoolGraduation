package com.example.demo.service;

import com.example.demo.mapper.AddressMapper;
import com.example.demo.pojo.AddressPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    public Integer add(AddressPojo addressPojo) {
        return addressMapper.add(addressPojo);
    }

    public List<AddressPojo> selectAll(Integer userid) {
        return addressMapper.selectAll(userid);
    }

    public Integer deleteAddress(Integer id) {
        return addressMapper.deleteAddress(id);
    }

    public int updateAddress(AddressPojo address) {
        return addressMapper.updateAddress(address);
    }

    public AddressPojo selectById(Integer id) {
        return addressMapper.selectById(id);
    }
}
