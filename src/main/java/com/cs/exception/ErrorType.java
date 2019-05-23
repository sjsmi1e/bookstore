package com.cs.exception;

/**
 * Created by smi1e
 * Date 2019/5/23 15:25
 * Description 异常枚举类型定义
 */
public enum ErrorType implements CommonError {

    //用户相关错误信息
    USER_ERROR_MESSAGE(10001,"用户基本信息错误"),
    USER_ERROR_NOTEXIT(10002,"用户不存在"),

    //商品错误相关信息
    BOOK_ERROR_MESSAGE(20001,"商品基本信息错误"),
    BOOK_ERROR_NOTEXIT(20002,"商品不存在"),
    ;

    private int errNum;
    private String errMsg;
    ErrorType(int i, String error) {
        this.errNum = i;
        this.errMsg = error;
    }

    @Override
    public int getErrCode() {
        return 0;
    }

    @Override
    public String getErrMsg() {
        return null;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        return null;
    }
}
