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
 * @Desc 服务消费者启动类
 */
@Slf4j
public class ConsumerBootstrap {

    public static void init() throws Exception {
        //Rpc框架初始化
        RpcApplication.init();
    }
}
