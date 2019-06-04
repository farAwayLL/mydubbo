package com.lang.api.controller.user;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lang.common.enums.StatusEnum;
import com.lang.common.response.R;
import com.lang.inter.user.UserService;
import com.lang.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author faraway
 * @date 2019/6/3 18:39
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Reference
    private UserService userService;

    @GetMapping("/getUserById/{id}")
    @ResponseBody
    public R getUserById(@PathVariable String id) {
        try {
            // 参数校验
            if (StringUtils.isBlank(id) || !NumberUtil.isNumber(id)) {
                return R.error(StatusEnum.INVALID_PARAM);
            } else {
                User user = userService.getUserById(id);
                if (user == null) {
                    return R.error(StatusEnum.NULL);
                }
                return R.ok(user);
            }
        } catch (Exception e) {
            logger.error("获取用户信息失败！", e);
            return R.error(StatusEnum.FAIL);
        }
    }

}
