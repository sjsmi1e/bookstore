package com.cs.controller;

import com.cs.controller.VOModel.BookVO;
import com.cs.exception.BookException;
import com.cs.exception.ErrorType;
import com.cs.pojo.Book;
import com.cs.response.CommonResponse;
import com.cs.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by smi1e
 * Date 2019/5/24 12:17
 * Description 书籍api
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/book")
public class BookController extends VOController {
    @Autowired
    private BookService bookService;

    @ResponseBody
    @RequestMapping(value = "/getBookById",method = RequestMethod.GET)
    public CommonResponse getBookById(Integer bookId) throws Exception {
        if (bookId==null || bookId<=0){
            throw new Exception("请求参数错误");
        }
        Book book = bookService.getBookById(bookId);
        if (book==null){
            throw new BookException(ErrorType.BOOK_ERROR_NOTEXIT);
        }else {
            BookVO bookVO = bookVOconvertFromPojo(book);
            return CommonResponse.createResponse(200,bookVO);
        }
    }
}
