package com.cs.pojo;

import lombok.Data;

/**
 * Created by smi1e
 * Date 2019/5/24 10:14
 * Description 购物车
 */
@Data
public class ShoppingCart {
    private Integer cartId;
    private Integer userId;
    private Integer bookId;
    private Integer bookCount;
}
