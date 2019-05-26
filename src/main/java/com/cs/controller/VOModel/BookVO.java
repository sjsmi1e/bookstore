package com.cs.controller.VOModel;

import lombok.Data;

/**
 * Created by smi1e
 * Date 2019/5/24 12:06
 * Description
 */
@Data
public class BookVO {
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
    private String bookAuthor;
    private String bookDesc;
}
