package com.cs.controller;

import com.cs.pojo.Customer;
import com.cs.response.CommonResponse;
import com.cs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by smi1e
 * Date 2019/5/23 15:52
 * Description 用户模块api
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/getAllCustomer")
    public CommonResponse getCustomer(){
        List<Customer> customer = userService.getCustomer();
        return CommonResponse.createResponse(200,customer);
    }
}
