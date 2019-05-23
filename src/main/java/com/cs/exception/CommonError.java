package com.cs.exception;

/**
 * Created by smi1e
 * Date 2019/5/23 15:23
 * Description 装饰器 异常处理接口
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
