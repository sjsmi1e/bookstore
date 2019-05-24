package com.cs.service;

import com.cs.mapper.UserMapper;
import com.cs.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by smi1e
 * Date 2019/5/23 16:45
 * Description user服务类
 */
@Service
public class UserService implements UserMapper {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<Customer> getCustomer() {
        return userMapper.getCustomer();
    }

    @Override
    public Integer logIn(String userEmail, String userPassword) {
        return userMapper.logIn(userEmail,userPassword);
    }
}
