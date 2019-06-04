package com.lang.model.dto;

import java.io.Serializable;

/**
 * @author faraway
 * @date 2019/5/25 13:45
 */
public class ProductType implements Serializable {

    private String secondProductType;

    private String secondProductTypeName;

    public String getSecondProductType() {
        return secondProductType;
    }

    public void setSecondProductType(String secondProductType) {
        this.secondProductType = secondProductType;
    }

    public String getSecondProductTypeName() {
        return secondProductTypeName;
    }

    public void setSecondProductTypeName(String secondProductTypeName) {
        this.secondProductTypeName = secondProductTypeName;
    }
}
