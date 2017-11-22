package com.lakala.common.constant;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by user on 2017/3/27.
 */
public enum EFlag {
    NORMAL("正常", 1),
    DELETE("删除", 2);
    //名称
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer value;

    EFlag(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static EFlag fromValue(Integer value) {
        for (EFlag c : EFlag.values()) {
            if (Objects.equals(c.getValue(), value)) {
                return c;
            }
        }

        return NORMAL;
    }

    public static EFlag fromName(String name) {
        for (EFlag c : EFlag.values()) {
            if (Objects.equals(c.getName(), name)) {
                return c;
            }
        }

        return NORMAL;
    }

}
