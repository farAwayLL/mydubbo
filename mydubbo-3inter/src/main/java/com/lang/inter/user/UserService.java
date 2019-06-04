package com.lang.inter.user;

import com.lang.model.entity.User;

/**
 * @author faraway
 * @date 2019/5/21 17:44
 */
public interface UserService {

    User getUserById(String id);

    User selectUserByUsername(String username);
}
