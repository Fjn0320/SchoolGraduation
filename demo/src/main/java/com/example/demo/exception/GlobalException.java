package com.example.demo.exception;


import com.example.demo.common.WrapMapper;
import com.example.demo.common.Wrapper;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Wrapper serviceException(ServiceException e) {
        return new Wrapper(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Wrapper globalException(Exception e) {
        e.printStackTrace();
        return new Wrapper(500, "系统错误");
    }
}
