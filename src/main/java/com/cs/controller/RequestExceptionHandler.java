package com.cs.controller;

import com.cs.response.CommonResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.ErrorHandler;

/**
 * Created by smi1e
 * Date 2019/5/23 15:08
 * Description url 404 错误页面
 */
@RestController
public class RequestExceptionHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public CommonResponse errorJsonPage(){
        return CommonResponse.createResponse(404,"url不存在");
    }
}
