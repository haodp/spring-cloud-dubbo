package com.lakala.model.output;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2017/4/6.
 */
@Data
public class UserInfo implements Serializable {
    private Integer id;
    private String username;
    private String realName;
    private Integer gender;
    private String phone;
    private String email;
    private String picUrl;
}
