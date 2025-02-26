package com.ayouok;

import com.ayouok.bootstarp.ConsumerBootstrap;
import com.ayouok.model.User;
import com.ayouok.proxy.ServiceProxyFactory;
import com.ayouok.service.UserService;

/**
 * 简易服务消费者示例
 * @author ayouokk
 */
public class EasyConsumerExample {

    public static void main(String[] args) throws Exception {
        //初始化rpc
        ConsumerBootstrap.init();
        //获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = userService.getUser(new User("bayou"));
        System.out.println(user);
    }
}
