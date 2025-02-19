package com.ayouok.proxy;

import cn.hutool.core.collection.CollectionUtil;
import com.ayouok.RpcApplication;
import com.ayouok.config.RpcConfig;
import com.ayouok.model.RpcRequest;
import com.ayouok.model.RpcResponse;
import com.ayouok.model.ServiceMetaInfo;
import com.ayouok.registry.Registry;
import com.ayouok.registry.RegistryFactory;
import com.ayouok.server.tcp.VertxTcpClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author dxn
 * @date 2024年12月12日 17:23
 * @apiNote
 */
@Slf4j
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        //获取注册中心地址
        ServiceMetaInfo serviceMetaInfo = getProviderService(rpcRequest.getServiceName());
        try {
            //发送tcp请求
            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, serviceMetaInfo);
            return rpcResponse.getData();
        } catch (Exception e) {
            log.error("VertxTcpClient 调用失败");
        }
        return null;
    }

    private ServiceMetaInfo getProviderService(String serviceName) {
        //获取rpc配置
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        //获取注册中心实例
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        //注册中心初始化
        registry.init(rpcConfig.getRegistryConfig());
        //获取服务元数据并返回
        List<ServiceMetaInfo> serviceMetaInfos = registry.serviceDiscovery(serviceName);
        if (CollectionUtil.isEmpty(serviceMetaInfos)){
            throw new RuntimeException("没有找到服务");
        }
        return serviceMetaInfos.get(0);
    }
}
