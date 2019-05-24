package com.cs.pojo;

import lombok.Data;

/**
 * Created by smi1e
 * Date 2019/5/24 10:12
 * Description
 */
@Data
public class Remark {
    private Integer remarkId;
    private Integer userId;
    private Integer bookId;
    private Integer starNum;
    private String content;
    private String remarkImg;
    private String createTime;
}
