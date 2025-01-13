package com.ayouok.service;

import com.ayouok.model.User;

/**
 * 用户服务
 * @author ayouokk
 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    default Integer getNumber(){
        return 1;
    }
}
