/*
 *
 * Copyright 2017-2018 549477611@qq.com(xiaoyu)
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.lakala.transaction.core.bean;


import java.io.Serializable;
import java.util.Date;

public class TransactionRecover implements Serializable {

    private static final long serialVersionUID = -3262858695515766275L;
    /**
     * 主键id
     */
    private String id;


    /**
     * 重试次数，
     */
    private int retriedCount = 0;

    /**
     * 创建时间
     */
    private Date createTime = new Date();


    /**
     * 创建时间
     */
    private Date lastTime = new Date();

    /**
     * 版本控制 防止并发问题
     */
    private int version = 1;

    /**
     * 事务组id
     */
    private String groupId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 事务执行方法
     */
    private TransactionInvocation transactionInvocation;


    /**
     *  {@linkplain com.lakala.transaction.common.enums.TransactionStatusEnum}
     */
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public int getRetriedCount() {
        return retriedCount;
    }

    public void setRetriedCount(int retriedCount) {
        this.retriedCount = retriedCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public TransactionInvocation getTransactionInvocation() {
        return transactionInvocation;
    }

    public void setTransactionInvocation(TransactionInvocation transactionInvocation) {
        this.transactionInvocation = transactionInvocation;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
