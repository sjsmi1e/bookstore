package com.cs.controller;

import com.cs.exception.ErrorType;
import com.cs.exception.UserException;
import com.cs.response.CommonResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by smi1e
 * Date 2019/5/23 15:15
 * Description 异常处理页面，可自定义异常
 */
@ControllerAdvice
public class MyExceptionHandler{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResponse exceptionHandler(Exception e){
        return CommonResponse.createResponse(9999,e.getMessage());
    }

    @ExceptionHandler(UserException.class)
    @ResponseBody
    public CommonResponse pageErrorHandller(Exception e){
        return CommonResponse.createResponse(ErrorType.USER_ERROR_NOTEXIT.getErrCode(),e.getMessage());
    }
}
