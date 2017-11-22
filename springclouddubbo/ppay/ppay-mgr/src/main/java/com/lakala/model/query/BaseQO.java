package com.lakala.model.query;

import lombok.Data;

/**
 * Created by user on 2017/4/11.
 */
@Data
public abstract class BaseQO {
    private Integer begin;
    private Integer pageSize;
}
