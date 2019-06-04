package com.lang.user.service;

import cn.hutool.json.JSONUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.lang.inter.user.UserService;
import com.lang.model.entity.User;
import com.lang.model.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author faraway
 * @date 2019/3/4 15:44
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(String id) {
        logger.info("用户id是{}", id);
        User user = userMapper.selectByPrimaryKey(Integer.valueOf(id));
        logger.info("id为{}的用户信息为：{}", id, JSONUtil.toJsonStr(user));
        return user;
    }

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }
}
