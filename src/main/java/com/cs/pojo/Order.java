package com.cs.pojo;

import lombok.Data;

/**
 * Created by smi1e
 * Date 2019/5/24 10:08
 * Description
 */
@Data
public class Order {
    private Integer orderId;
    private Integer buyUserId;
    private Integer sellUserId;
    private Integer bookId;
    private Integer bookCount;
    private String createTime;
    private String orderNum;//订单编号
    private String buyAddr;
    private String orderDesc;//订单描述

}
