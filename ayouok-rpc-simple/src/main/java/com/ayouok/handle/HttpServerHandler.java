package com.ayouok.handle;

import cn.hutool.core.util.ObjectUtil;
import com.ayouok.model.RpcRequest;
import com.ayouok.model.RpcResponse;
import com.ayouok.registry.LocalRegistry;
import com.ayouok.serializer.JdkSerializer;
import com.ayouok.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;

/**
 * @author dxn
 * @date 2024年12月12日 15:28
 * @apiNote
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest httpServerRequest) {
        JdkSerializer jdkSerializer = new JdkSerializer();
        httpServerRequest.bodyHandler(buffer -> {
            // 反序列化
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = jdkSerializer.deserialize(buffer.getBytes(), RpcRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //创建响应对象
            RpcResponse rpcResponse = new RpcResponse();
            if (ObjectUtil.isEmpty(rpcRequest)) {
                //如果为null,直接返回
                rpcResponse.setMessage("请求为空");
                doResponse(httpServerRequest, rpcResponse, jdkSerializer);
            }
            if (ObjectUtil.isEmpty(rpcRequest.getServiceName())) {
                //如果为null,直接返回
                rpcResponse.setMessage("请求为空");
                doResponse(httpServerRequest, rpcResponse, jdkSerializer);
            }
            //获取class对象
            Class<?> serviceClass = LocalRegistry.get(rpcRequest.getServiceName());
            try {
                //反射创建对象
                Object service = serviceClass.newInstance();
                //执行方法
                Object result = serviceClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes()).invoke(service, rpcRequest.getArgs());
                //设置返回值
                rpcResponse.setData(result);
                rpcResponse.setDataType(result.getClass());
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(rpcRequest.getMethodName());
                rpcResponse.setException(e);
            }
        });
    }

    private void doResponse(HttpServerRequest httpServerRequest, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse response = httpServerRequest.response();
        response.putHeader("content-type", "application/json");
        try {
            byte[] bytes = serializer.serialize(rpcResponse);
            response.end(Buffer.buffer(bytes));
        } catch (IOException e) {
            e.printStackTrace();
            response.end(Buffer.buffer());
        }
    }

}
