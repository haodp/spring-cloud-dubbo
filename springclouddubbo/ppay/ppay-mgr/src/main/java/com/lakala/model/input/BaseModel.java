package com.lakala.model.input;

import lombok.Data;

/**
 * Created by user on 2017/4/1.
 */
@Data
public abstract class BaseModel {
    private Integer pageSize;
    private Integer pageNo;
}
