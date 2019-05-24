package com.cs.pojo;

import lombok.Data;

/**
 * Created by smi1e
 * Date 2019/5/24 9:56
 * Description
 */
@Data
public class Book {
    private Integer bookId;
    private String bookName;
    private String bookImage;
    private Double bookPrice;
    private String bookPresstime;
    private Integer bookPages;
    private Integer bookType;
    private Integer bookPurchaseNum;
    private Integer userId;
    private String bookPress;
}
