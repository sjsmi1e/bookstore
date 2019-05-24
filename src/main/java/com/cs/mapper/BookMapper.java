package com.cs.mapper;

import com.cs.pojo.Book;
import org.apache.ibatis.annotations.*;

/**
 * Created by smi1e
 * Date 2019/5/24 11:43
 * Description
 */
@Mapper
public interface BookMapper {

    @Select("select book_id,book_name,book_image,book_price,book_press,book_presstime,book_pages,book_type,book_purchase_num,user_id from book where book_id=#{book_id}")
    @Results({
            @Result(property = "bookId",column = "book_id"),
            @Result(property = "bookName",column = "book_name"),
            @Result(property = "bookImage",column = "book_image"),
            @Result(property = "bookPrice",column = "book_price"),
            @Result(property = "bookPress",column = "book_press"),
            @Result(property = "bookPresstime",column = "book_presstime"),
            @Result(property = "bookPages",column = "book_pages"),
            @Result(property = "bookType",column = "book_type"),
            @Result(property = "bookPurchaseNum",column = "book_purchase_num"),
            @Result(property = "userId",column = "user_id")
    })
    public Book getBookById(@Param("book_id")Integer book_id);
}
