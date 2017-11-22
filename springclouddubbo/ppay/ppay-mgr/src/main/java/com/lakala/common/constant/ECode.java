package com.lakala.common.constant;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by user on 2017/3/27.
 */
public enum ECode {
    OK("成功", 0),
    FAILED("失败", 1),
    EXCEPTION("异常", 2),
    ERROR("错误", 3);
    //名称
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer code;

    ECode(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public static ECode fromCode(Integer code) {
        for (ECode c : ECode.values()) {
            if (Objects.equals(c.getCode(), code)) {
                return c;
            }
        }

        return OK;
    }

    public static ECode fromName(String name) {
        for (ECode c : ECode.values()) {
            if (Objects.equals(c.getName(), name)) {
                return c;
            }
        }

        return OK;
    }

}
