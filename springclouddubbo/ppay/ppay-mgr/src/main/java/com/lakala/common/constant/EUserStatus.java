package com.lakala.common.constant;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by user on 2017/3/27.
 */
public enum EUserStatus {
    NORMAL("正常", 1),
    PENDING("未激活", 2),
    DELETE("删除", 3);
    //名称
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer value;

    EUserStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static EUserStatus fromValue(Integer value) {
        for (EUserStatus c : EUserStatus.values()) {
            if (Objects.equals(c.getValue(), value)) {
                return c;
            }
        }

        return NORMAL;
    }

    public static EUserStatus fromName(String name) {
        for (EUserStatus c : EUserStatus.values()) {
            if (Objects.equals(c.getName(), name)) {
                return c;
            }
        }

        return NORMAL;
    }

}
