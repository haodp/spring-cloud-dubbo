package com.lakala.model.output;

import lombok.Data;

/**
 * Created by Administrator on 2017/3/23.
 */
@Data
public class UserVO {

    private Integer userId;
    private String username;
    private String realName;
    private Integer genderId;
    private Integer roleId;
    private String roleName;
    private String phone;
    private String email;
    private String userGender;
    private String userStatus;
}
