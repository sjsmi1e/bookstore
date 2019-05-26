package com.cs.mapper;

import com.cs.pojo.Customer;
import com.cs.pojo.Order;
import com.cs.pojo.User;
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

    /**
     * 获取用户填写的地址
     * @param userId
     * @return
     */
    @Select("select user_addr from user_addr where user_id=#{userId}")
    public List<String> getAddrByUserId(@Param("userId")Integer userId);

    @Select("select id,user_name,user_avatar,user_sex,user_birth,user_area,user_occupation,user_introduction,user_email,user_password " +
            "from user where id=#{userId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "userName",column = "user_name"),
            @Result(property = "userAvatar",column = "user_avatar"),
            @Result(property = "userSex",column = "user_sex"),
            @Result(property = "userBirth",column = "user_birth"),
            @Result(property = "userArea",column = "user_area"),
            @Result(property = "userOccupation",column = "user_occupation"),
            @Result(property = "userIntroduction",column = "user_introduction"),
            @Result(property = "userEmail",column = "user_email"),
            @Result(property = "userPassword",column = "user_password")
    })
    public User getUserById(@Param("userId")Integer userId);

    @Insert("insert into `order` values(default,#{order.buyUserId},#{order.bookId},#{order.bookCount},#{order.createTime}" +
            ",#{order.orderNum},#{order.sellUserId},#{order.buyAddr})")
    public Integer placeOrder(@Param("order")Order order);
}
