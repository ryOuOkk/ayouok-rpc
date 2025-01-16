package com.ayouok.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.ayouok.RpcApplication;
import com.ayouok.model.RpcRequest;
import com.ayouok.model.RpcResponse;
import com.ayouok.serializer.Serializer;
import com.ayouok.serializer.SerializerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author dxn
 * @date 2024年12月12日 17:23
 * @apiNote
 */
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        byte[] requestBytes = serializer.serialize(rpcRequest);
        try {
            HttpResponse response = HttpRequest.post("http://localhost:8080")
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
}
