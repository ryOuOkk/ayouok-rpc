package com.ayouok;

import com.ayouok.config.RpcConfig;
import com.ayouok.constant.RpcConstant;
import com.ayouok.model.User;
import com.ayouok.proxy.ServiceProxyFactory;
import com.ayouok.service.UserService;
import com.ayouok.utils.ConfigUtil;

/**
 * 简易服务消费者示例
 * @author ayouokk
 */
public class EasyConsumerExample {

    public static void main(String[] args) {
        RpcConfig rpcConfig = ConfigUtil.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        System.out.println(rpcConfig.getName());
        System.out.println(rpcConfig.getVersion());
        System.out.println(rpcConfig.getServerPort());
    }
}
