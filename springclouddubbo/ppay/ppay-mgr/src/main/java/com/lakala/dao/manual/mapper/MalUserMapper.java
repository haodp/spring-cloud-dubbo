package com.lakala.dao.manual.mapper;

import com.lakala.dao.manual.model.MalUser;
import com.lakala.model.query.UserQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by user on 2017/3/27.
 */
public interface MalUserMapper {
    List<MalUser> getUserList(UserQO user);
    Integer getUserTotalCount(UserQO user);
}
