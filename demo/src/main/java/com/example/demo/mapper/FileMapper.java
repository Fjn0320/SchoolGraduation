package com.example.demo.mapper;


import com.example.demo.pojo.FilePojo;

import java.util.List;

public interface FileMapper {

    int insert(FilePojo filePojo);

    FilePojo selectMD5(String md5);

    List<FilePojo> getPageAllFile(Integer pageNum, Integer pageSize, String name);

    int getFileSum(String name);

    Integer deleteFile(Integer id);

    Integer update(int id, Boolean enable);

    int fileAllDelete(List<Integer> list);
}
