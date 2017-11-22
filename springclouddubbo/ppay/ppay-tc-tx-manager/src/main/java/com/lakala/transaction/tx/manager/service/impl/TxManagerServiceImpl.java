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
package com.lakala.transaction.tx.manager.service.impl;

import com.lakala.transaction.common.enums.TransactionRoleEnum;
import com.lakala.transaction.common.enums.TransactionStatusEnum;
import com.lakala.transaction.common.netty.bean.TxTransactionGroup;
import com.lakala.transaction.common.netty.bean.TxTransactionItem;
import com.lakala.transaction.tx.manager.config.Constant;
import com.lakala.transaction.tx.manager.service.TxManagerService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("unchecked")
public class TxManagerServiceImpl implements TxManagerService {


    private final RedisTemplate redisTemplate;


    @Autowired
    public TxManagerServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 保存事务组 在事务发起方的时候进行调用
     *
     * @param txTransactionGroup 事务组
     * @return true 成功 false 失败
     */
    @Override

    public Boolean saveTxTransactionGroup(TxTransactionGroup txTransactionGroup) {
        try {
            final String groupId = txTransactionGroup.getId();
            final List<TxTransactionItem> itemList = txTransactionGroup.getItemList();
            if (CollectionUtils.isNotEmpty(itemList)) {
                for (TxTransactionItem item : itemList) {
                    redisTemplate.opsForHash().put(cacheKey(groupId), item.getTaskKey(), item);
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 往事务组添加事务
     *
     * @param txGroupId         事务组id
     * @param txTransactionItem 子事务项
     * @return true 成功 false 失败
     */
    @Override
    public Boolean addTxTransaction(String txGroupId, TxTransactionItem txTransactionItem) {
        try {
            redisTemplate.opsForHash().put(cacheKey(txGroupId), txTransactionItem.getTaskKey(), txTransactionItem);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根据事务组id 获取所有的子项目  我觉得要排除掉第一个，因为第一个不需要进行处理 （它就是事务组信息）
     *
     * @param txGroupId 事务组id
     * @return List<TxTransactionItem>
     */
    @Override
    public List<TxTransactionItem> listByTxGroupId(String txGroupId) {
        final Map<Object, TxTransactionItem> entries = redisTemplate.opsForHash().entries(cacheKey(txGroupId));
        final Collection<TxTransactionItem> values = entries.values();
        return new ArrayList<>(values);


    }

    /**
     * 删除事务组信息  当回滚的时候 或者事务组完全提交的时候
     *
     * @param txGroupId txGroupId 事务组id
     */
    @Override
    public void removeRedisByTxGroupId(String txGroupId) {
        redisTemplate.delete(cacheKey(txGroupId));
    }

    /**
     * 更新事务状态
     *
     * @param key     redis key 也就是txGroupId
     * @param hashKey 也就是taskKey
     * @param status  事务状态
     * @return true 成功 false 失败
     */
    @Override
    public Boolean updateTxTransactionItemStatus(String key, String hashKey, int status) {
        try {
            final TxTransactionItem item = (TxTransactionItem)
                    redisTemplate.opsForHash().get(cacheKey(key), hashKey);
           /* TxTransactionItem item = new TxTransactionItem();
            BeanUtils.copyProperties(object, item);*/
            item.setStatus(status);
            redisTemplate.opsForHash().put(cacheKey(key), item.getTaskKey(), item);
        } catch (BeansException e) {
            return false;
        }
        return true;
    }

    @Override
    public int findTxTransactionGroupStatus(String txGroupId) {
        try {
            final TxTransactionItem item = (TxTransactionItem)
                    redisTemplate.opsForHash().get(cacheKey(txGroupId), txGroupId);
           /* TxTransactionItem item = new TxTransactionItem();
            BeanUtils.copyProperties(object, item);*/
            return item.getStatus();
        } catch (BeansException e) {
            e.printStackTrace();
            return TransactionStatusEnum.ROLLBACK.getCode();
        }
    }

    @Override
    public Boolean removeCommitTxGroup() {
        final Set<String> keys = redisTemplate.keys(Constant.REDIS_KEYS);
        keys.parallelStream().forEach(key -> {
            final Map<Object, TxTransactionItem> entries = redisTemplate.opsForHash().entries(key);
            final Collection<TxTransactionItem> values = entries.values();
            final boolean present = values.stream()
                    .anyMatch(item -> item.getStatus() != TransactionStatusEnum.COMMIT.getCode());
            if (!present) {
                redisTemplate.delete(key);
            }
        });

        return true;
    }

    /**
     * 删除回滚的事务组
     *
     * @return true 成功  false 失败
     */
    @Override
    public Boolean removeRollBackTxGroup() {
        final Set<String> keys = redisTemplate.keys(Constant.REDIS_KEYS);
        keys.parallelStream().forEach(key -> {
            final Map<Object, TxTransactionItem> entries = redisTemplate.opsForHash().entries(key);
            final Collection<TxTransactionItem> values = entries.values();
            final Optional<TxTransactionItem> any =
                    values.stream().filter(item -> item.getRole() == TransactionRoleEnum.START.getCode()
                            && item.getStatus() == TransactionStatusEnum.ROLLBACK.getCode())
                            .findAny();
            if (any.isPresent()) {
                redisTemplate.delete(key);
            }
        });

        return true;
    }

    private String cacheKey(String key) {
        return String.format(Constant.REDIS_PRE_FIX, key);
    }
}
