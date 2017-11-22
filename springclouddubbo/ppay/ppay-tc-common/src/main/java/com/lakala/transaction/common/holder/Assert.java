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
package com.lakala.transaction.common.holder;


import com.lakala.transaction.common.exception.TransactionRuntimeException;


public class Assert {

    private Assert() {

    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new TransactionRuntimeException(message);
        }
    }

    public static void notNull(Object obj) {
        if (obj == null) {
            throw new TransactionRuntimeException("argument invalid,Please check");
        }
    }

    public static void checkConditionArgument(boolean condition, String message) {
        if (!condition) {
            throw new TransactionRuntimeException(message);
        }
    }

}
