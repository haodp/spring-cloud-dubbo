package com.lakala.common.constant;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by user on 2017/3/27.
 */
public enum EGender {
    MALE("男", 1),
    FAMALE("女", 2);
    //名称
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer value;

    EGender(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static EGender fromValue(Integer value) {
        for (EGender c : EGender.values()) {
            if (Objects.equals(c.getValue(), value)) {
                return c;
            }
        }

        return MALE;
    }

    public static EGender fromName(String name) {
        for (EGender c : EGender.values()) {
            if (Objects.equals(c.getName(), name)) {
                return c;
            }
        }

        return MALE;
    }

}
