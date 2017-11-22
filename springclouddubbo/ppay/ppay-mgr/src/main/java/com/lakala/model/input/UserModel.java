package com.lakala.model.input;

import lombok.Data;

/**
 * Created by user on 2017/3/27.
 */
@Data
public class UserModel extends BaseModel {

    private Integer id;
    private String username;
    private String password;
    private String realName;
    private Integer role;
    private Integer gender;
    private String phone;
    private String email;
}
