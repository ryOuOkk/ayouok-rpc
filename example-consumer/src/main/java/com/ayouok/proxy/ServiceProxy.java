package com.ayouok.proxy;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.ayouok.RpcApplication;
import com.ayouok.config.RpcConfig;
import com.ayouok.constant.RpcConstant;
import com.ayouok.model.RpcRequest;
import com.ayouok.model.RpcResponse;
import com.ayouok.model.ServiceMetaInfo;
import com.ayouok.registry.Registry;
import com.ayouok.registry.RegistryFactory;
import com.ayouok.serializer.Serializer;
import com.ayouok.serializer.SerializerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author dxn
 * @date 2024年12月12日 17:23
 * @apiNote
 */
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //获取序列化器
        Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        //构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        //获取字节数组
        byte[] requestBytes = serializer.serialize(rpcRequest);
        //获取注册中心地址
        ServiceMetaInfo serviceMetaInfo = getProviderService(rpcRequest.getServiceName());
        try {
            HttpResponse response = HttpRequest.post(serviceMetaInfo.getServiceAddress())
                    .body(requestBytes)
                    .execute();
            byte[] responseBodys = response.bodyBytes();
            RpcResponse rpcResponse = serializer.deserialize(responseBodys, RpcResponse.class);
            return rpcResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
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
