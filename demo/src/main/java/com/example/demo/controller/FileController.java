package com.example.demo.controller;


import cn.hutool.core.io.FileUtil;
import com.example.demo.common.AuthAccess;
import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;


@RestController
@RequestMapping("/file")//给统一的接口加前缀
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + "\\file\\";

    @AuthAccess
    @PostMapping("/upload")
    public Wrapper<String> upload(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();//获取文件的原始名称 aaa.png
        String mainName = FileUtil.mainName(filename);//文件的主要名字 aaa
        String extName = FileUtil.extName(filename);//文件的后缀 png
        //如果文件不存在就创建
        if (!FileUtil.exist(ROOT_PATH)){
            FileUtil.mkdir(ROOT_PATH);
        }
        //然后要判断存入的文件是否有相同的,有相同的就重命名一个文件名称
        if(FileUtil.exist(ROOT_PATH + filename)){
            filename = System.currentTimeMillis()+"_"+mainName+"."+extName;
        }
        File saveFile = new File(ROOT_PATH + filename);
        file.transferTo(saveFile);//存储到本地磁盘
        String url = "http://"+ip+":"+port+"/wm/file/download/" + filename;

        return WrapMapper.ok(url);//返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
    }

    @AuthAccess
    @GetMapping("/download/{filename}")
    public void download(@PathVariable String filename,HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));  // 附件下载
        String filePath = ROOT_PATH + filename;
        if (!FileUtil.exist(filePath)){
            return;
        }

        byte[] bytes = FileUtil.readBytes(filePath);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);//数组就是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }


}
