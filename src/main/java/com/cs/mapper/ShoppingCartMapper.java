package com.cs.mapper;

import com.cs.controller.VOModel.ShoppingCartBook;
import com.cs.pojo.Book;
import com.cs.pojo.Order;
import com.cs.pojo.ShoppingCart;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * Created by smi1e
 * Date 2019/5/24 14:15
 * Description 购物车Mapper
 */
@Mapper
public interface ShoppingCartMapper {
    /**
     * 查询用户购物车
     * @param userId
     * @return
     */
    @Select("SELECT book_desc,book_author,book.book_id,book_name,book_image,book_price,book_presstime,book_pages,book_type,book_purchase_num,shopping_cart.user_id,book_press,cart_id FROM book INNER JOIN shopping_cart " +
            "WHERE book.book_id = shopping_cart.book_id and shopping_cart.user_id=#{userId}")
    @Results({
            @Result(property = "bookId",column = "book_id"),
            @Result(property = "bookName",column = "book_name"),
            @Result(property = "bookImage",column = "book_image"),
            @Result(property = "bookPrice",column = "book_price"),
            @Result(property = "bookPresstime",column = "book_presstime"),
            @Result(property = "bookPages",column = "book_pages"),
            @Result(property = "bookType",column = "book_type"),
            @Result(property = "bookPurchaseNum",column = "book_purchase_num"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "bookPress",column = "book_press"),
            @Result(property = "shoppingCartId",column = "cart_id"),
            @Result(property = "bookAuthor",column = "book_author"),
            @Result(property = "bookDesc",column = "book_desc")
    })
    public List<ShoppingCartBook> getAllBooksFromShoppingCart(@Param("userId")Integer userId);

    /**
     * 同过删除购物车商品
     * @param cartId
     * @return
     */
    @Delete("DELETE FROM shopping_cart WHERE cart_id = #{cartId}")
    public Integer delBookFromShoppingCart(@Param("cartId")Integer cartId);

    /**
     * 购物车下单
     * @param order
     * @param cartId
     * @return
     */
    @Select({"CALL placeOrder(#{order.buyUserId},#{order.bookId.bookId},#{order.bookCount},#{order.orderNum},#{order.sellUserId}," +
            "#{order.buyAddr},#{order.orderDesc},#{cartId})"})
    @Options(statementType = StatementType.CALLABLE)
    Integer placeOrder(@Param("order")Order order,@Param("cartId")Integer cartId);

    @Insert("insert into shopping_cart values(default,#{shoppingCart.userId},#{shoppingCart.bookId},#{shoppingCart.bookCount})")
    Integer addCart(@Param("shoppingCart")ShoppingCart shoppingCart);
}
