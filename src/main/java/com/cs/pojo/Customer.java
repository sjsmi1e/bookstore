package com.cs.pojo;

import lombok.Data;

/**
 * Created by smi1e
 * Date 2019/5/23 16:14
 * Description customer实体类
 */
@Data
public class Customer {
    private Integer id;
    private String userName;
    private String passwd;
    private String phone;
    private String address;
    private String email;
    private String code;
    private Integer actived;
}
