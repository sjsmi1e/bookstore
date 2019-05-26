package com.cs.pojo;

import com.cs.controller.VOModel.UserVO;
import lombok.Data;

/**
 * Created by smi1e
 * Date 2019/5/24 10:12
 * Description
 */
@Data
public class Remark {
    private Integer remarkId;
    private UserVO user;
    private Integer bookId;
    private Integer starNum;
    private String content;
    private String remarkImg;
    private String createTime;
}
