package com.example.demo.exception;


import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{

    private  int code = 500;

    public ServiceException(String msg) {
        super(msg);
        this.code=500;
    }
    public ServiceException(int code,String msg) {
        super(msg);
        this.code=code;
    }
}
