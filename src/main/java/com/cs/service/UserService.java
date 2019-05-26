package com.cs.service;

import com.cs.controller.VOModel.UserVO;
import com.cs.mapper.ShoppingCartMapper;
import com.cs.mapper.UserMapper;
import com.cs.pojo.Customer;
import com.cs.pojo.Order;
import com.cs.pojo.User;
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
    public Integer logIn(String userEmail, String userPassword) {
        return userMapper.logIn(userEmail,userPassword);
    }

    @Override
    public List<String> getAddrByUserId(Integer userId) {
        return userMapper.getAddrByUserId(userId);
    }

    @Override
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public UserVO getUserById2(Integer userId) {
        return userMapper.getUserById2(userId);
    }

    @Override
    public Integer placeOrder(Order order) {
        return userMapper.placeOrder(order);
    }
}
