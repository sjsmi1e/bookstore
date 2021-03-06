package com.cs.controller;

import com.alibaba.fastjson.JSONArray;
import com.cs.controller.VOModel.ShoppingCartBook;
import com.cs.exception.ErrorType;
import com.cs.exception.UserException;
import com.cs.pojo.Book;
import com.cs.pojo.Customer;
import com.cs.pojo.Order;
import com.cs.pojo.User;
import com.cs.response.CommonResponse;
import com.cs.service.BookService;
import com.cs.service.UserService;
import com.cs.util.FormateTime;
import com.cs.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by smi1e
 * Date 2019/5/23 15:52
 * Description 用户模块api
 */
@Controller
@RequestMapping("/user")
@Log4j2
public class UserController extends VOController{

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    private Lock lock = new ReentrantLock();


    /**
     * 登录
     * @param request
     * @param userName
     * @param userPassword
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResponse logIn(HttpServletRequest request, HttpServletResponse response, String userName, String userPassword) throws Exception {
        if (userName==null || userPassword==null || userName.equals("") || userPassword.equals("")){
            throw new UserException(ErrorType.USER_ERROR_NOTEXIT,ErrorType.USER_ERROR_NOTEXIT.getErrMsg());
        }else {
            //加锁避免线程安全问题
            lock.lock();
            try {
                Integer id = userService.logIn(userName, userPassword);
                if (id == null){
                    throw new Exception("用户名密码错误");
                }else {
                    request.getSession().setAttribute("user_id",id);
                    response.addCookie(new Cookie("user_id",String.valueOf(id)));
                    return new CommonResponse(200,id);
                }
            }catch (Exception e){
                log.error(e.getMessage());
            }finally {
                lock.unlock();
            }
            return CommonResponse.createResponse(200,"未知错误");
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse logOut(HttpServletResponse response,HttpServletRequest request){
        HttpSession session = request.getSession(false);//防止创建Session
        if(session == null){
            return CommonResponse.createResponse(200,"注销成功");
        }
        session.removeAttribute("user_id");
        return CommonResponse.createResponse(200,"注销成功");
    }

    /**
     * 通过用户id获取用户信息
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getUserById",method = RequestMethod.GET)
    public CommonResponse getUserById(String userId) throws Exception {
        if (userId==null || userId.equals("") || !StringUtil.isInteger(userId)){
            throw new Exception("输入参数不正确");
        }else {
            return CommonResponse.createResponse(200,userVOconvertFromPojo(userService.getUserById(Integer.valueOf(userId))));
        }
    }

    @RequestMapping(value = "/getUserIdSession",method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getSessionUserId(HttpServletRequest request){
        return CommonResponse.createResponse(200,request.getSession().getAttribute("user_id"));
    }

    /**
     * 通过用户id获取用户购物车
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/shoppingCart",method = RequestMethod.GET)
    public CommonResponse getAllBooksFromShoppingCart(String userId) throws Exception {
        if (userId==null || userId.equals("") || !StringUtil.isInteger(userId)){
            throw new Exception("输入参数错误");
        }
        return CommonResponse.createResponse(200,bookService.getAllBooksFromShoppingCart(Integer.valueOf(userId)));
    }

    /**
     * 通过cartID删除购物车商品信息
     * @param cartId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/delBookFromCart",method = RequestMethod.GET)
    public CommonResponse delBookFromCart(String cartId) throws Exception {
        if (cartId==null || cartId.equals("") || !StringUtil.isInteger(cartId)){
            throw new Exception("输入参数错误");
        }
        if (bookService.delBookFromShoppingCart(Integer.valueOf(cartId))==null){
            return CommonResponse.createResponse(200,"fail");
        }else {
            return CommonResponse.createResponse(200,"success");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getAddrByUserId",method = RequestMethod.GET)
    public CommonResponse getAddrByUserId(String userId) throws Exception {
        if (userId==null || userId.equals("") || !StringUtil.isInteger(userId)){
            throw new Exception("输入参数错误");
        }else {
            return CommonResponse.createResponse(200,userService.getAddrByUserId(Integer.valueOf(userId)));
        }
    }

    /**
     * 购物车下单
     * @param books
     * @param buyId
     * @param addr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/placeOrder",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse placeOrder(String books,String buyId,String addr,String desc) throws Exception {
        if (books==null || buyId==null || addr==null || books.equals("") || !StringUtil.isInteger(buyId) || addr.equals("")){
            throw new Exception("输入参数错误");
        }
        //加锁，线程安全
        lock.lock();
        try {
            //处理逻辑
            List<ShoppingCartBook> orderBook = JSONArray.parseArray(books, ShoppingCartBook.class);
            boolean flag = true;
            for (ShoppingCartBook book:orderBook){
                Order tempOrder = new Order();
                tempOrder.setBookCount(1);
                tempOrder.setBookId(bookService.getBookById(book.getBookId()));
                tempOrder.setSellUserId(book.getUserId());
                tempOrder.setCreateTime(FormateTime.getData());
                tempOrder.setBuyAddr(addr);
                tempOrder.setOrderDesc(desc);
                tempOrder.setBuyUserId(Integer.valueOf(buyId));
                tempOrder.setOrderNum(UUID.randomUUID().toString());
                if (bookService.placeOrder(tempOrder,book.getShoppingCartId())!=0){
                    flag = false;
                    break;
                }
            }
            if (flag)
                return CommonResponse.createResponse(200,"success");
        }catch (Exception e){
            return CommonResponse.createResponse(200,"fail");
        }finally {
            lock.unlock();
        }
        return CommonResponse.createResponse(200,"fail");
    }

    /**
     * 下单
     * @param userId
     * @param bookId
     * @param bookCount
     * @param buyAddr
     * @param orderDesc
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public CommonResponse order(String userId,String bookId,String bookCount,String buyAddr,String orderDesc) throws Exception {
        if (userId.equals("") || userId==null || bookId.equals("") || bookId==null || bookCount.equals("") || bookCount==null
        || buyAddr.equals("") || buyAddr==null || !StringUtil.isInteger(userId) || !StringUtil.isInteger(bookId) || !StringUtil.isInteger(bookCount)){
            throw new Exception("输入参数不正确");
        }
        lock.lock();
        try {
            Order order = new Order();
            order.setBuyUserId(Integer.valueOf(userId));
            order.setBookId(bookService.getBookById(Integer.valueOf(bookId)));
            order.setOrderDesc(orderDesc);
            order.setOrderNum(UUID.randomUUID().toString());
            order.setBookCount(Integer.valueOf(bookCount));
            order.setBuyAddr(buyAddr);
            if (userService.placeOrder(order)==1){
                return CommonResponse.createResponse(200,"success");
            }
            return CommonResponse.createResponse(200,"fail");
        }finally {
            lock.unlock();
        }
    }

    /**
     * 获取订单信息
     * @param userId
     * @param pageNo
     * @param pageNum
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOrder",method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getOrder(String userId,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageNum) throws Exception {
        if (userId.equals("")||userId==null||!StringUtil.isInteger(userId)){
            throw new Exception("输入参数错误");
        }
        PageHelper.startPage(pageNo,pageNum,true);
        return CommonResponse.createResponse(200,new PageInfo<>(userService.getOrderByUserId(Integer.valueOf(userId))));
    }

    /**
     * 更新用户信息
     * @param userId
     * @param userName
     * @param userImage
     * @param userSex
     * @param userBirth
     * @param userArea
     * @param userOccu
     * @param userEmail
     * @param userDesc
     * @return
     */
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse updateUser(String userId,String userName,String userImage,String userSex,String userBirth,String userArea,
                                     String userOccu,String userEmail,String userDesc) throws Exception {
        if (userId.equals("")||userId==null||!StringUtil.isInteger(userId)||userName.equals("")||userName==null||userSex.equals("")
        ||userSex==null||userEmail.equals("")||userEmail==null){
            throw new Exception("输入参数错误");
        }
        lock.lock();
        try {
            User user = new User();
            user.setId(Integer.valueOf(userId));
            user.setUserName(userName);
            user.setUserAvatar(userImage);
            user.setUserSex(userSex);
            user.setUserBirth(userBirth);
            user.setUserArea(userArea);
            user.setUserOccupation(userOccu);
            user.setUserEmail(userEmail);
            user.setUserIntroduction(userDesc);
            return CommonResponse.createResponse(200,userService.updateUser(user));
        }finally {
            lock.unlock();
        }

    }

    /**
     * 通过id获取有多少人下单了
     * @param userId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBuyOrder",method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getBuyOrder(String userId) throws Exception {
        if (userId.equals("")||userId==null||!StringUtil.isInteger(userId)){
            throw new Exception("参数输入错误");
        }
        return CommonResponse.createResponse(200,bookService.getbuyOrder(Integer.valueOf(userId)));
    }


    @RequestMapping(value = "/getBuyOrderInfo",method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getBuyOrderInfo(String userId,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageNum) throws Exception {
        if (userId.equals("")||userId==null||!StringUtil.isInteger(userId)){
            throw new Exception("参数输入错误");
        }
        PageHelper.startPage(pageNo,pageNum,true);
        return CommonResponse.createResponse(200,new PageInfo<>(userService.getOrderBysellUserId(Integer.valueOf(userId))));
    }

}
