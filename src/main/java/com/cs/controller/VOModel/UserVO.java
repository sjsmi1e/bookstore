package com.cs.controller.VOModel;

import lombok.Data;

/**
 * Created by smi1e
 * Date 2019/5/25 15:18
 * Description
 */
@Data
public class UserVO {
    private Integer id;
    private String userName;
    private String userAvatar;
    private String userSex;
    private String userBirth;
    private String userArea;
    private String userOccupation;
    private String userIntroduction;
    private String userEmail;
}
