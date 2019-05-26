package com.cs.util;

import java.util.Date;

/**
 * Created by smi1e
 * Date 2019/5/25 16:16
 * Description
 */
public class FormateTime {
    public static String getData(){
        return new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    public static String getData2(){
        return new java.text.SimpleDateFormat("yyyy-MM-ddhh:mm:ss").format(new Date());
    }
}
