package com.cs.controller;

import com.cs.controller.VOModel.BookVO;
import com.cs.controller.VOModel.UserVO;
import com.cs.pojo.Book;
import com.cs.pojo.User;
import org.springframework.beans.BeanUtils;

/**
 * Created by smi1e
 * Date 2019/5/24 12:05
 * Description 用于pojo转vo
 */
public class VOController {
    public BookVO bookVOconvertFromPojo(Book book){
        if(book == null){
            return null;
        }
        BookVO bookVO =  new BookVO();
        BeanUtils.copyProperties(book,bookVO);
        return bookVO;
    }
    public UserVO userVOconvertFromPojo(User user){
        if (user == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }
}
