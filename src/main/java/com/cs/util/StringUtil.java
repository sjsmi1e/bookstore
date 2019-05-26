package com.cs.util;

import java.util.regex.Pattern;

/**
 * Created by smi1e
 * Date 2019/5/26 22:49
 * Description
 */
public class StringUtil {

    /**
     * 判断字符串是否是整数
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
