package com.lakala.dao.gen.mapper;

import com.lakala.dao.gen.model.TUserRole;
import com.lakala.dao.gen.model.TUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    long countByExample(TUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    int deleteByExample(TUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    int deleteByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    int insert(TUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    int insertSelective(TUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    List<TUserRole> selectByExample(TUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    TUserRole selectByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    int updateByExampleSelective(@Param("record") TUserRole record, @Param("example") TUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    int updateByExample(@Param("record") TUserRole record, @Param("example") TUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    int updateByPrimaryKeySelective(TUserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    int updateByPrimaryKey(TUserRole record);
}