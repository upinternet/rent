package com.hyh.ease.rent.exception.impl;

import com.hyh.ease.rent.exception.ApiException;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException(Long errorCode,String message,Object data,Throwable e){
        super(errorCode, message , data ,e);
    }
}
