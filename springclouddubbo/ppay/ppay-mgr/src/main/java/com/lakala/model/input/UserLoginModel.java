package com.lakala.model.input;

import lombok.Data;

/**
 * Created by user on 2017/4/6.
 */
@Data
public class UserLoginModel {
    private String username;
    private String password;
    private boolean remember;
}
