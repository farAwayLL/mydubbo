package com.lang.common.response;

import com.lang.common.enums.StatusEnum;

/**
 * @author faraway
 * @date 2019/5/24 14:11
 */
public class DataResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    public DataResponse(StatusEnum statusEnum) {
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getMsg();
    }

    public DataResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DataResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
