package com.cs.controller.VOModel;

import com.cs.pojo.Book;
import lombok.Data;

/**
 * Created by smi1e
 * Date 2019/5/24 15:28
 * Description
 */
@Data
public class ShoppingCartBook extends Book {
    private Integer shoppingCartId;
}
