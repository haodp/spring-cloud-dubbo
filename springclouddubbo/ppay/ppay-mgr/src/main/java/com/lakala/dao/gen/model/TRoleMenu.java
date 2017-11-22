package com.lakala.dao.gen.model;

import java.math.BigDecimal;
import java.util.Date;

public class TRoleMenu {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ROLE_MENU.UUID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private String uuid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ROLE_MENU.ROLE_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private BigDecimal roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ROLE_MENU.MENU_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private BigDecimal menuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ROLE_MENU.CREATE_USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private BigDecimal createUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ROLE_MENU.CREATE_TIME
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_ROLE_MENU.FLAG
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    private BigDecimal flag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ROLE_MENU.UUID
     *
     * @return the value of T_ROLE_MENU.UUID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ROLE_MENU.UUID
     *
     * @param uuid the value for T_ROLE_MENU.UUID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ROLE_MENU.ROLE_ID
     *
     * @return the value of T_ROLE_MENU.ROLE_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public BigDecimal getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ROLE_MENU.ROLE_ID
     *
     * @param roleId the value for T_ROLE_MENU.ROLE_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ROLE_MENU.MENU_ID
     *
     * @return the value of T_ROLE_MENU.MENU_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public BigDecimal getMenuId() {
        return menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ROLE_MENU.MENU_ID
     *
     * @param menuId the value for T_ROLE_MENU.MENU_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setMenuId(BigDecimal menuId) {
        this.menuId = menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ROLE_MENU.CREATE_USER_ID
     *
     * @return the value of T_ROLE_MENU.CREATE_USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public BigDecimal getCreateUserId() {
        return createUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ROLE_MENU.CREATE_USER_ID
     *
     * @param createUserId the value for T_ROLE_MENU.CREATE_USER_ID
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setCreateUserId(BigDecimal createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ROLE_MENU.CREATE_TIME
     *
     * @return the value of T_ROLE_MENU.CREATE_TIME
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ROLE_MENU.CREATE_TIME
     *
     * @param createTime the value for T_ROLE_MENU.CREATE_TIME
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_ROLE_MENU.FLAG
     *
     * @return the value of T_ROLE_MENU.FLAG
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public BigDecimal getFlag() {
        return flag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_ROLE_MENU.FLAG
     *
     * @param flag the value for T_ROLE_MENU.FLAG
     *
     * @mbg.generated Wed Oct 11 14:36:25 CST 2017
     */
    public void setFlag(BigDecimal flag) {
        this.flag = flag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_ROLE_MENU
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
        sb.append(", roleId=").append(roleId);
        sb.append(", menuId=").append(menuId);
        sb.append(", createUserId=").append(createUserId);
        sb.append(", createTime=").append(createTime);
        sb.append(", flag=").append(flag);
        sb.append("]");
        return sb.toString();
    }
}