package com.cs.exception;

/**
 * Created by smi1e
 * Date 2019/5/23 15:42
 * Description
 */
public class BookException extends Exception implements CommonError {
    private CommonError commonError;
    public BookException(CommonError commonError) {
        super(commonError.getErrMsg());
        this.commonError = commonError;
    }

    public BookException(CommonError commonError ,String erroeMsg) {
        super(commonError.getErrMsg());
        this.commonError = commonError;
        this.setErrMsg(erroeMsg);
    }

    @Override
    public int getErrCode() {
        return commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        return commonError.setErrMsg(errMsg);
    }
}
