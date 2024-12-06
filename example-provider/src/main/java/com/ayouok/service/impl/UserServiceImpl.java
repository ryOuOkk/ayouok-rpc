package com.ayouok.service.impl;

import com.ayouok.model.User;
import com.ayouok.service.UserService;

/**
 * @author ayouok
 * @create 2024-12-06-15:14
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
