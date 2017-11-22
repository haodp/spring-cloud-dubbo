package com.lakala.model.output;

import java.util.List;

import lombok.Data;

/**
 * Created by user on 2017/3/29.
 */
@Data
public class UserData extends BaseData {
    public List<UserVO> userList;
}
