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
package com.lakala.transaction.core.service.impl;

import com.lakala.transaction.core.bean.TxTransactionInfo;
import com.lakala.transaction.core.constant.Constant;
import com.lakala.transaction.core.service.TxTransactionFactoryService;
import com.lakala.transaction.core.service.handler.ActorTxTransactionHandler;
import com.lakala.transaction.core.service.handler.InsideCompensationHandler;
import com.lakala.transaction.core.service.handler.StartCompensationHandler;
import com.lakala.transaction.core.service.handler.StartTxTransactionHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TxTransactionFactoryServiceImpl implements TxTransactionFactoryService {

    @Override
    public Class factoryOf(TxTransactionInfo info) throws Throwable {
        if (StringUtils.isNoneBlank(info.getCompensationId())) {
            return StartCompensationHandler.class;
        }
        if (StringUtils.isBlank(info.getTxGroupId())) {
            return StartTxTransactionHandler.class;
        } else {
            if (Objects.equals(Constant.COMPENSATE_ID, info.getTxGroupId())) {
                return InsideCompensationHandler.class;
            }
            return ActorTxTransactionHandler.class;
        }

    }
}
