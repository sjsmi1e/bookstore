package com.cs.mapper;

import com.cs.pojo.Customer;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

/**
 * Created by smi1e
 * Date 2019/5/23 16:18
 * Description usermapper
 */

@Mapper
public interface UserMapper {
    @Select("select * from customers")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "userName",column = "username"),
            @Result(property = "passwd",column = "PASSWORD"),
            @Result(property = "address",column = "address"),
            @Result(property = "email",column = "email"),
            @Result(property = "code",column = "CODE"),
            @Result(property = "actived",column = "actived")
    })
    public List<Customer> getCustomer();

    /**
     *
     * @param userEmail
     * @param userPassword
     * @return userId
     */
    @Select("select id from user where user_email=#{userEmail} and user_password=#{userPassword}")
    public Integer logIn(@Param("userEmail")String userEmail,@Param("userPassword")String userPassword);
}
