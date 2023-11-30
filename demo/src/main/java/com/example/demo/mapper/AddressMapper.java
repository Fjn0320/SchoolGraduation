package com.example.demo.mapper;


import com.example.demo.pojo.AddressPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {

    Integer add(AddressPojo addressPojo);

    List<AddressPojo> selectAll(Integer userid);

    Integer deleteAddress(Integer id);

    int updateAddress(AddressPojo address);

    AddressPojo selectById(Integer id);
}

