package com.cs.mapper;

import com.cs.controller.VOModel.UserVO;
import com.cs.pojo.Customer;
import com.cs.pojo.Order;
import com.cs.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

/**
 * Created by smi1e
 * Date 2019/5/23 16:18
 * Description usermapper
 */

@Mapper
public interface UserMapper {

    /**
     * 注册
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

    /**
     * 通过userId获取用户信息
     * @param userId
     * @return
     */
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

    /**
     * 通过userId获取用户信息(除了密码)
     * @param userId
     * @return
     */
    @Select("select id,user_name,user_avatar,user_sex,user_birth,user_area,user_occupation,user_introduction,user_email " +
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
    public UserVO getUserById2(@Param("userId")Integer userId);

    /**
     * 直接下单
     * @param order
     * @return
     */
    @Select({"CALL `Order`(#{order.buyUserId},#{order.bookId.bookId},#{order.bookCount},#{order.orderNum},#{order.buyAddr},#{order.orderDesc})"})
    @Options(statementType = StatementType.CALLABLE)
    public Integer placeOrder(@Param("order")Order order);

    /**
     * 获取订单信息
     * @param buyUserId
     * @return
     */
    @Select("SELECT * FROM `order` WHERE buy_user_id=#{buyUserId}")
    @Results({
            @Result(property = "bookId",column = "book_id",one = @One(select = "com.cs.mapper.BookMapper.getBookById")),
            @Result(property = "orderId",column = "order_id"),
            @Result(property = "buyUserId",column = "buy_user_id"),
            @Result(property = "sellUserId",column = "sell_user_id"),
            @Result(property = "bookCount",column = "book_count"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "orderNum",column = "order_num"),
            @Result(property = "buyAddr",column = "buy_addr"),
            @Result(property = "orderDesc",column = "order_desc")
    })
    List<Order> getOrderByUserId(@Param("buyUserId")Integer buyUserId);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Update("UPDATE `user` SET user_name=#{user.userName},user_avatar=#{user.userAvatar},user_sex=#{user.userSex},user_birth=#{user.userBirth}," +
            "user_area=#{user.userArea},user_occupation=#{user.userOccupation},user_introduction=#{user.userIntroduction},user_email=#{user.userEmail} " +
            "WHERE id=#{user.id}")
    Integer updateUser(@Param("user")User user);
}

