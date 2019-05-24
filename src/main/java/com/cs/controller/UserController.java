package com.cs.controller;

import com.cs.exception.ErrorType;
import com.cs.exception.UserException;
import com.cs.pojo.Book;
import com.cs.pojo.Customer;
import com.cs.response.CommonResponse;
import com.cs.service.BookService;
import com.cs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by smi1e
 * Date 2019/5/23 15:52
 * Description 用户模块api
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController extends VOController{

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @ResponseBody
    @RequestMapping("/getAllCustomer")
    public CommonResponse getCustomer(){
        List<Customer> customer = userService.getCustomer();
        return CommonResponse.createResponse(200,customer);
    }

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
    public CommonResponse logIn(HttpServletRequest request, String userName, String userPassword) throws Exception {
        if (userName==null || userPassword==null || userName=="" || userPassword==""){
            throw new UserException(ErrorType.USER_ERROR_NOTEXIT,ErrorType.USER_ERROR_NOTEXIT.getErrMsg());
        }else {
            Integer id = userService.logIn(userName, userPassword);
            if (id == null){
                throw new Exception("用户名密码错误");
            }else {
                request.getSession().setAttribute("id",id);
                return new CommonResponse(200,id);
            }
        }
    }

    /**
     * 通过用户id获取用户购物车
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/shoppingCart",method = RequestMethod.GET)
    public CommonResponse getAllBooksFromShoppingCart(Integer userId) throws Exception {
        if (userId==null){
            throw new Exception("输入参数错误");
        }
        return CommonResponse.createResponse(200,bookService.getAllBooksFromShoppingCart(userId));
    }

    /**
     * 通过cartID删除购物车商品信息
     * @param cartId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/delBookFromCart",method = RequestMethod.GET)
    public CommonResponse delBookFromCart(Integer cartId) throws Exception {
        if (cartId==null){
            throw new Exception("输入参数错误");
        }
        Integer integer = bookService.delBookFromShoppingCart(cartId);
        if (integer==null){
            return CommonResponse.createResponse(200,"fail");
        }else {
            return CommonResponse.createResponse(200,"success");
        }
    }

}
