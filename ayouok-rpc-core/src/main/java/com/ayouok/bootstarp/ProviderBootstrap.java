package com.ayouok.bootstarp;

import com.ayouok.RpcApplication;
import com.ayouok.config.RegistryConfig;
import com.ayouok.config.RpcConfig;
import com.ayouok.model.ServiceMetaInfo;
import com.ayouok.model.ServiceRegisterInfo;
import com.ayouok.registry.LocalRegistry;
import com.ayouok.registry.Registry;
import com.ayouok.registry.RegistryFactory;
import com.ayouok.server.HttpServer;
import com.ayouok.server.http.VertxHttpServer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author ayouok
 * @create 2025-02-26-19:47
 * @Desc 服务提供者启动类
 */
@Slf4j
public class ProviderBootstrap {

    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) throws Exception {
        //Rpc框架初始化
        RpcApplication.init();
        //获取全局配置
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        //将服务提供者需要注册的服务注册
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            // 注册服务到本地
            LocalRegistry.register(serviceRegisterInfo.getServiceName(), serviceRegisterInfo.getImplClass());
            //注册到注册中心
            //通过全局配置获取注册中心实例
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            registry.init(registryConfig);
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceRegisterInfo.getServiceName());
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
                log.info("服务:{},注册成功", serviceMetaInfo.getServiceName());
            } catch (Exception e) {
                log.error("注册中心注册失败", e);
                throw new RuntimeException(e);
            }
        }
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
