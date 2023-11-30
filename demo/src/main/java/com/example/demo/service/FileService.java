package com.example.demo.service;

import com.example.demo.common.Wrapper;
import com.example.demo.mapper.FileMapper;
import com.example.demo.pojo.FilePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    public FilePojo insertFile(FilePojo filePojo) {
        fileMapper.insert(filePojo);
       return filePojo;
    }

    public FilePojo selectMD5(String md5) {
        FilePojo filePojo = fileMapper.selectMD5(md5);
        return filePojo;
    }

    public List<FilePojo> getPageAllFile(Integer pageNum, Integer pageSize, String name) {
        return fileMapper.getPageAllFile(pageNum,pageSize,name);
    }

    public int getFileSum(String name) {
        return fileMapper.getFileSum(name);
    }

    public Integer deleteFile(Integer deleteId) {
        return fileMapper.deleteFile(deleteId);
    }

    public Integer update(int id, Boolean enable) {
        return fileMapper.update(id,enable);
    }

    public int fileAllDelete(List<Integer> list) {
        return fileMapper.fileAllDelete(list);
    }
}
