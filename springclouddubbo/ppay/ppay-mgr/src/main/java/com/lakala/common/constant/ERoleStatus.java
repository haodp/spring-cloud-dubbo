package com.lakala.common.constant;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by user on 2017/3/30.
 */
public enum ERoleStatus {

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

    ERoleStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static ERoleStatus fromValue(Integer value) {
        for (ERoleStatus c : ERoleStatus.values()) {
            if (Objects.equals(c.getValue(), value)) {
                return c;
            }
        }

        return NORMAL;
    }

    public static ERoleStatus fromName(String name) {
        for (ERoleStatus c : ERoleStatus.values()) {
            if (Objects.equals(c.getName(), name)) {
                return c;
            }
        }

        return NORMAL;
    }
}
