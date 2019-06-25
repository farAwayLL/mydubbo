package com.lang.common.response;

import com.lang.common.enums.StatusEnum;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public R() {
        put("code", StatusEnum.SUCCESS.getCode());
        put("msg", StatusEnum.SUCCESS.getMsg());
    }

    public static R error(StatusEnum statusEnum) {
        R r = new R();
        r.put("code", statusEnum.getCode());
        r.put("msg", statusEnum.getMsg());
        return r;
    }

    public static R ok() {
        return new R();
    }

    @SuppressWarnings("unchecked")
    public static R ok(Object data) {
        R r = new R();
        Map dataMap = new HashMap();
        dataMap.put("data", data);
        r.putAll(dataMap);
        return r;
    }

}
