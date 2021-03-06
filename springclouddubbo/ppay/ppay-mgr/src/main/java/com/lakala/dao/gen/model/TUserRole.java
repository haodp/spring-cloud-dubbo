package com.lakala.dao.gen.model;

import java.math.BigDecimal;
import java.util.Date;

public class TUserRole {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_USER_ROLE.UUID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private String uuid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_USER_ROLE.USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private BigDecimal userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_USER_ROLE.ROLE_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private BigDecimal roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_USER_ROLE.CREATE_USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private BigDecimal createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_USER_ROLE.CREATE_TIME
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_USER_ROLE.MODIFY_USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private BigDecimal modifyUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_USER_ROLE.MODIFY_TIME
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private Date modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_USER_ROLE.FLAG
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private BigDecimal flag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_USER_ROLE.UUID
     *
     * @return the value of T_USER_ROLE.UUID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_USER_ROLE.UUID
     *
     * @param uuid the value for T_USER_ROLE.UUID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_USER_ROLE.USER_ID
     *
     * @return the value of T_USER_ROLE.USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public BigDecimal getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_USER_ROLE.USER_ID
     *
     * @param userId the value for T_USER_ROLE.USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_USER_ROLE.ROLE_ID
     *
     * @return the value of T_USER_ROLE.ROLE_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public BigDecimal getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_USER_ROLE.ROLE_ID
     *
     * @param roleId the value for T_USER_ROLE.ROLE_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_USER_ROLE.CREATE_USER_ID
     *
     * @return the value of T_USER_ROLE.CREATE_USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public BigDecimal getCreateUserId() {
        return createUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_USER_ROLE.CREATE_USER_ID
     *
     * @param createUserId the value for T_USER_ROLE.CREATE_USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setCreateUserId(BigDecimal createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_USER_ROLE.CREATE_TIME
     *
     * @return the value of T_USER_ROLE.CREATE_TIME
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_USER_ROLE.CREATE_TIME
     *
     * @param createTime the value for T_USER_ROLE.CREATE_TIME
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_USER_ROLE.MODIFY_USER_ID
     *
     * @return the value of T_USER_ROLE.MODIFY_USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public BigDecimal getModifyUserId() {
        return modifyUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_USER_ROLE.MODIFY_USER_ID
     *
     * @param modifyUserId the value for T_USER_ROLE.MODIFY_USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setModifyUserId(BigDecimal modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_USER_ROLE.MODIFY_TIME
     *
     * @return the value of T_USER_ROLE.MODIFY_TIME
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_USER_ROLE.MODIFY_TIME
     *
     * @param modifyTime the value for T_USER_ROLE.MODIFY_TIME
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_USER_ROLE.FLAG
     *
     * @return the value of T_USER_ROLE.FLAG
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public BigDecimal getFlag() {
        return flag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_USER_ROLE.FLAG
     *
     * @param flag the value for T_USER_ROLE.FLAG
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setFlag(BigDecimal flag) {
        this.flag = flag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_USER_ROLE
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uuid=").append(uuid);
        sb.append(", userId=").append(userId);
        sb.append(", roleId=").append(roleId);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyUserId=").append(modifyUserId);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", flag=").append(flag);
        sb.append("]");
        return sb.toString();
    }
}