package com.cs.service;

import com.cs.controller.VOModel.ShoppingCartBook;
import com.cs.mapper.BookMapper;
import com.cs.mapper.ShoppingCartMapper;
import com.cs.pojo.Book;
import com.cs.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by smi1e
 * Date 2019/5/24 12:16
 * Description
 */
@Service
public class BookService implements BookMapper,ShoppingCartMapper {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public Book getBookById(Integer book_id) {
        return bookMapper.getBookById(book_id);
    }

    @Override
    public List<Book> searchBook(String book_name) {
        return bookMapper.searchBook(book_name);
    }

    @Override
    public List<ShoppingCartBook> getAllBooksFromShoppingCart(Integer userId) {
        return shoppingCartMapper.getAllBooksFromShoppingCart(userId);
    }

    @Override
    public Integer delBookFromShoppingCart(Integer cartId) {
        return shoppingCartMapper.delBookFromShoppingCart(cartId);
    }

    @Override
    public Integer placeOrder(Order order, Integer cartId) {
        return shoppingCartMapper.placeOrder(order,cartId);
    }
}
