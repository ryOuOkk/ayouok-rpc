package com.ayouok;

import cn.hutool.core.util.ObjectUtil;
import com.ayouok.config.RegistryConfig;
import com.ayouok.config.RpcConfig;
import com.ayouok.constant.RpcConstant;
import com.ayouok.registry.Registry;
import com.ayouok.registry.RegistryFactory;
import com.ayouok.utils.ConfigUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ayouok
 * @create 2024-12-30-11:01
 */
@Slf4j
public class RpcApplication {

    /**
     * rpc配置类
     */
    private static volatile RpcConfig rpcConfig;

    /**
     * 初始化
     * @param newRpcConfig RpcConfig
     */
    public static void init (RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        //注册JVM关闭钩子，在程序退出时调用注册中心的销毁方法
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    /**
     * 初始化
     */
    public static void init  () {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtil.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            newRpcConfig = new RpcConfig();
        }
        init (newRpcConfig);
    }

    /**
     * 获取rpc配置
     * @return RpcConfig
     */
    public static RpcConfig getRpcConfig() {
        if (ObjectUtil.isEmpty(rpcConfig)){
            synchronized (RpcConfig.class){
                if (ObjectUtil.isEmpty(rpcConfig)){
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
