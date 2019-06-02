package com.cs.controller;

import com.cs.controller.VOModel.BookVO;
import com.cs.controller.VOModel.UserVO;
import com.cs.exception.BookException;
import com.cs.exception.ErrorType;
import com.cs.pojo.Book;
import com.cs.pojo.BookType;
import com.cs.pojo.Remark;
import com.cs.pojo.ShoppingCart;
import com.cs.response.CommonResponse;
import com.cs.service.BookService;
import com.cs.util.FileUtil;
import com.cs.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by smi1e
 * Date 2019/5/24 12:17
 * Description 书籍api
 */
@Controller
@RequestMapping("/book")
public class BookController extends VOController {
    @Autowired
    private BookService bookService;
    private Lock lock = new ReentrantLock();

    @ResponseBody
    @RequestMapping(value = "/getBookById",method = RequestMethod.GET)
    public CommonResponse getBookById(String bookId) throws Exception {
        if (bookId==null || bookId.equals("") || !StringUtil.isInteger(bookId)){
            throw new Exception("请求参数错误");
        }
        lock.lock();
        try {
            Book book = bookService.getBookById(Integer.valueOf(bookId));
            if (book==null){
                throw new BookException(ErrorType.BOOK_ERROR_NOTEXIT);
            }else {
                BookVO bookVO = bookVOconvertFromPojo(book);
                return CommonResponse.createResponse(200,bookVO);
            }
        }finally {
            lock.unlock();
        }

    }

    /**
     * 模糊查询
     * @param bookName
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public CommonResponse searchBook(String bookName,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "5") int pageNum) throws Exception {
        if (bookName==null || bookName.equals("")){
            throw new Exception("输入参数不正确");
        }
        PageHelper.startPage(pageNo,pageNum,true);
        return CommonResponse.createResponse(200,new PageInfo<>(bookService.searchBook(bookName)));
    }

    /**
     * 热门购买书籍前6本
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/top6Book",method = RequestMethod.GET)
    public CommonResponse top6Book(){
        return CommonResponse.createResponse(200,bookService.top6Book());
    }
    /**
     * 热门购买书籍前15本
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/top15Book",method = RequestMethod.GET)
    public CommonResponse top15Book(){
        return CommonResponse.createResponse(200,bookService.top15Book());
    }

    /**
     * 热门购买书籍前8本
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/topBook",method = RequestMethod.GET)
    public CommonResponse topBook(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "8") int pageNum){
        PageHelper.startPage(pageNo,pageNum,true);
        return CommonResponse.createResponse(200,new PageInfo<>(bookService.topBook()));
    }

    @ResponseBody
    @RequestMapping(value = "/typeTop6Book",method = RequestMethod.GET)
    public CommonResponse typeTop6Book(String type) throws Exception {
        if (type==null || type.equals("") || Integer.valueOf(type)<=0 || Integer.valueOf(type)>15){
            throw new Exception("参数输入错误");
        }
        return CommonResponse.createResponse(200,bookService.typeTop6Book(Integer.valueOf(type)));
    }

    @ResponseBody
    @RequestMapping(value = "/typeAllBook",method = RequestMethod.GET)
    public CommonResponse typeAllBook(String type,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "8") int pageNum) throws Exception {
        if (type==null || type.equals("") || Integer.valueOf(type)<=0 || Integer.valueOf(type)>15){
            throw new Exception("参数输入错误");
        }
        PageHelper.startPage(pageNo,pageNum,true);
        return CommonResponse.createResponse(200,new PageInfo<>(bookService.typeAllBook(Integer.valueOf(type))));
    }

    /**
     * 通过bookId获取评论信息
     * @param bookId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRemarkByBookId",method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getRemarkByBookId(String bookId, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "2") int pageNum) throws Exception {
        if (bookId==null || bookId.equals("") || !StringUtil.isInteger(bookId) ){
            throw new Exception("输入参数不正确");
        }
        PageHelper.startPage(pageNo,pageNum,true);
        return CommonResponse.createResponse(200,new PageInfo<>(bookService.getRemarkByBookId(Integer.valueOf(bookId))));
    }

    /**
     * 添加评论
     * @param userId
     * @param bookId
     * @param content
     * @param starNum
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addRemark",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse addRemark(String userId, String bookId, String content, String starNum) throws Exception {

        if (userId==null || userId.equals("") || bookId==null || bookId.equals("") || !StringUtil.isInteger(userId) || !StringUtil.isInteger(bookId)){
            throw new Exception("输入参数不合法");
        }
        if (content.equals("")||content==null){
            throw new Exception("内容不能为空");
        }

        //加锁，线程安全
        lock.lock();
        try {
            Remark remark = new Remark();
            UserVO userVO = new UserVO();
            userVO.setId(Integer.valueOf(userId));
            remark.setBookId(Integer.valueOf(bookId));
            remark.setContent(content);
            remark.setRemarkImg("");
            remark.setStarNum(Integer.valueOf(starNum));
            remark.setUser(userVO);
            if (bookService.addRemark(remark)==1){

                return CommonResponse.createResponse(200,"success");
            }
            return CommonResponse.createResponse(200,"fail");
        }finally {
            lock.unlock();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addCart",method = RequestMethod.POST)
    public CommonResponse addCart(String userId,String bookId,String bookCount) throws Exception {
        if (userId.equals("") || userId==null || bookId.equals("") || bookId==null || bookCount.equals("") || bookCount==null){
            throw new Exception("输入参数不正确");
        }
        lock.lock();
        try{
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setBookCount(Integer.valueOf(bookCount));
            shoppingCart.setBookId(Integer.valueOf(bookId));
            shoppingCart.setUserId(Integer.valueOf(userId));
            if (bookService.addCart(shoppingCart)==1){
                return CommonResponse.createResponse(200,"success");
            }
            return CommonResponse.createResponse(200,"fail");
        }finally {
            lock.unlock();
        }

    }

    /**
     * 获取书籍
     * @param userId
     * @param pageNo
     * @param pageNum
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAllBookByUserId",method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getAllBookByUserId(String userId,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageNum) throws Exception {
        if (userId.equals("") || userId==null|| !StringUtil.isInteger(userId)){
            throw new Exception("输入参数错误");
        }
        PageHelper.startPage(pageNo,pageNum,true);
        return CommonResponse.createResponse(200,new PageInfo<>(bookService.AllBookByUserId(Integer.valueOf(userId))));
    }

    /**
     * 删除书籍
     * @param bookId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delBookById")
    @ResponseBody
    public CommonResponse delBookById(String bookId) throws Exception {
        if (bookId.equals("")||bookId==null||!StringUtil.isInteger(bookId)){
            throw new Exception("输入参数错误");
        }
        return CommonResponse.createResponse(200,bookService.delBookById(Integer.valueOf(bookId)));
    }

    /**
     * 更新书籍
     * @param bookId
     * @param bookName
     * @param image
     * @param price
     * @param press
     * @param pages
     * @param type
     * @param author
     * @param desc
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateBook",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse uodateBook(String bookId,String bookName,String image,String price,String press,String pages,String type,
                                     String author,String desc) throws Exception {
        if (bookId.equals("")||bookId==null||!StringUtil.isInteger(bookId)||bookName.equals("")||bookName==null||price.equals("")
        ||price==null){
            throw new Exception("输入参数错误");
        }

        lock.lock();
        try {
            Book book = new Book();
            book.setBookId(Integer.valueOf(bookId));
            book.setBookName(bookName);
            book.setBookImage(image);
            book.setBookPrice(Double.valueOf(price));
            book.setBookPress(press);
            book.setBookPages(Integer.valueOf(pages));
            book.setBookType(Integer.valueOf(type));
            book.setBookAuthor(author);
            book.setBookDesc(desc);
            return CommonResponse.createResponse(200,bookService.updateBook(book));
        }finally {
            lock.unlock();
        }

    }

    /**
     * 添加书籍
     * @param bookName
     * @param image
     * @param price
     * @param press
     * @param pages
     * @param type
     * @param userId
     * @param author
     * @param desc
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addBook",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse addBook(String bookName,String image,String price,String press,String pages,String type,String userId
    ,String author,String desc) throws Exception {

        if (bookName.equals("")||bookName==null||price.equals("")||price==null||type.equals("")||type==null||!StringUtil.isInteger(type)||
        userId.equals("")||userId==null||!StringUtil.isInteger(userId)){
            throw new Exception("输入参数错误");
        }
        lock.lock();
        try {
            Book book = new Book();
            book.setBookName(bookName);
            book.setBookImage(image);
            book.setBookPrice(Double.valueOf(price));
            book.setBookPress(press);
            book.setBookPages(Integer.valueOf(pages));
            book.setBookType(Integer.valueOf(type));
            book.setUserId(Integer.valueOf(userId));
            book.setBookAuthor(author);
            book.setBookDesc(desc);
            return CommonResponse.createResponse(200,bookService.addBook(book));
        }finally {
            lock.unlock();
        }
    }

}
