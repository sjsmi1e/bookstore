package com.cs.response;

/**
 * Created by smi1e
 * Date 2019/5/23 15:04
 * Description 规范所有相应格式errCode+data
 */
public class CommonResponse {
    private int errCode;
    private Object data;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public CommonResponse(int errCode, Object data) {
        this.errCode = errCode;
        this.data = data;
    }

    public static CommonResponse createResponse(int errcode, Object data){
        return new CommonResponse(errcode,data);
    }
}
