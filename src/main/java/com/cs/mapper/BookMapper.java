package com.cs.mapper;

import com.cs.pojo.Book;
import com.cs.pojo.Remark;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by smi1e
 * Date 2019/5/24 11:43
 * Description
 */
@Mapper
public interface BookMapper {

    /**
     * bookId查询书的信息
     * @param book_id
     * @return
     */
    @Select("select book_desc,book_id,book_name,book_image,book_price,book_press,book_presstime,book_pages,book_type,book_purchase_num,user_id,book_author from book where book_id=#{book_id}")
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
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookAuthor",column = "book_author"),
            @Result(property = "bookDesc",column = "book_desc")
    })
    public Book getBookById(@Param("book_id")Integer book_id);

    /**
     * 书名模糊查询
     * @param book_name
     * @return
     */
    @Select("select book_desc,book_id,book_name,book_image,book_price,book_press,book_presstime,book_pages,book_type,book_purchase_num,user_id,book_author from book where book_name LIKE concat(concat('%',#{book_name}),'%')")
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
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookAuthor",column = "book_author"),
            @Result(property = "bookDesc",column = "book_desc")
    })
    public List<Book> searchBook(@Param("book_name")String book_name);


    /**
     * 热卖top6书籍
     * @return
     */
    @Select("select book_desc,book_id,book_name,book_image,book_price,book_press,book_presstime,book_pages,book_type,book_purchase_num," +
            "user_id,book_author from book ORDER BY book_purchase_num DESC LIMIT 6")
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
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookAuthor",column = "book_author"),
            @Result(property = "bookDesc",column = "book_desc")
    })
    List<Book> top6Book();

    /**
     * 通过bookId获取评论
     * @param bookId
     * @return
     */
    @Select("SELECT * from remark WHERE book_id = #{bookId}")
    @Results({
            @Result(property = "remarkId",column = "remark_id"),
            @Result(property = "user",column = "user_id",one = @One(select = "com.cs.mapper.UserMapper.getUserById2")),
            @Result(property = "bookId",column = "book_id"),
            @Result(property = "starNum",column = "star_num"),
            @Result(property = "content",column = "content"),
            @Result(property = "remarkImg",column = "remark_img"),
            @Result(property = "createTime",column = "create_time")
    })
    List<Remark> getRemarkByBookId(@Param("bookId")Integer bookId);

    /**
     * 添加评论
     * @param remark
     * @return
     */
    @Insert("INSERT INTO remark VALUES(DEFAULT,#{remark.user.id},#{remark.bookId},#{remark.starNum},#{remark.content},#{remark.remarkImg},DEFAULT)")
    Integer addRemark(@Param("remark")Remark remark);


    /**
     * 新书热卖
     * @return
     */
    @Select("SELECT book_desc,book_id,book_name,book_image,book_price,book_press,book_presstime,book_pages,book_type," +
            "book_purchase_num,user_id,book_author FROM `book` ORDER BY book_presstime desc,book_purchase_num desc LIMIT 15")
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
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookAuthor",column = "book_author"),
            @Result(property = "bookDesc",column = "book_desc")
    })
    List<Book> top15Book();


    /**
     * 新书推荐
     * @return
     */
    @Select("SELECT book_desc,book_id,book_name,book_image,book_price,book_press,book_presstime,book_pages,book_type," +
            "book_purchase_num,user_id,book_author FROM `book` ORDER BY book_presstime desc LIMIT 8")
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
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookAuthor",column = "book_author"),
            @Result(property = "bookDesc",column = "book_desc")
    })
    List<Book> top8Book();

    /**
     * 同类型书籍推荐（按照购买量）top6
     * @return
     */
    @Select("SELECT book_desc,book_id,book_name,book_image,book_price,book_press,book_presstime,book_pages,book_type,book_purchase_num,user_id,book_author FROM `book` WHERE book_type = #{type} ORDER BY book_purchase_num desc LIMIT 6")
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
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookAuthor",column = "book_author"),
            @Result(property = "bookDesc",column = "book_desc")
    })
    List<Book> typeTop6Book(@Param("type")Integer type);
}
