package com.lakala.model.output;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by user on 2017/3/27.
 */
@Data
public class Result<T> implements Serializable {

    private T data;
    private Integer code;
    private String msg;

}
