package com.ayouok;

import com.ayouok.model.User;
import com.ayouok.service.UserService;

/**
 * 简易服务消费者示例
 * @author ayouokk
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        // todo 需要获取 UserService 的实现类对象
        UserService userService = null;
        User user = new User();
        user.setName("yupi");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
