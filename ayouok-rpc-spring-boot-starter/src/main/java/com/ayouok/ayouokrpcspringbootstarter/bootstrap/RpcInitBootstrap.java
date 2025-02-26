package com.ayouok.ayouokrpcspringbootstarter.bootstrap;

import com.ayouok.RpcApplication;
import com.ayouok.ayouokrpcspringbootstarter.annotation.EnableAyouokRpc;
import com.ayouok.config.RpcConfig;
import com.ayouok.server.tcp.VertxTcpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;


/**
 * @author ayouok
 * @create 2025-02-26-20:21
 * @Desc Rpc 框架启动
 */
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {

    /**
     * Spring 初始化时执行，初始化 RPC 框架
     *
     * @param importingClassMetadata 注解信息
     * @param registry               bean信息注册器
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取 EnableRpc 注解的属性值
        boolean needServer = (boolean) Objects.requireNonNull(importingClassMetadata.getAnnotationAttributes(EnableAyouokRpc.class.getName()))
                .get("needServer");
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        // 启动服务器
        if (needServer) {
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            try {
                vertxTcpServer.doStart(rpcConfig.getServerPort());
            } catch (Exception e) {
                log.error("启动失败", e);
                throw new RuntimeException(e);
            }
        } else {
            log.info("不启动 server");
        }
    }
}
