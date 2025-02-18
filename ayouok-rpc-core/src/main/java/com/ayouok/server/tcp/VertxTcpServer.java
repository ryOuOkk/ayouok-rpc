package com.ayouok.server.tcp;


import com.ayouok.server.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author ayouok
 * @create 2025-02-18-21:43
 * @desc tcp服务处理
 */
@Slf4j
public class VertxTcpServer implements HttpServer {
    @Override
    public void doStart(int port) throws Exception {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();
        NetServer tcpServerNetServer = vertx.createNetServer();
        // 处理接收到的数据
        tcpServerNetServer.connectHandler(socket -> socket.handler(buffer -> {
            log.info("VertxTcpServer Received data: {}", buffer.toString());
            // 处理接收到的数据
            byte[] requestBytes = buffer.getBytes();
            byte[] responseBytes = handlerRequest(requestBytes);
            // 发送响应
            socket.write(Buffer.buffer(responseBytes));
            log.info("VertxTcpServer Response data: {}", Arrays.toString(responseBytes));
        }));
        // 监听端口
        tcpServerNetServer.listen(port, result -> {
            if (result.succeeded()) {
                log.info("VertxTcpServer listening on port {}", port);
            } else {
                log.error("Failed to start VertxTcpServer", result.cause());
            }
        });
    }

    private byte[] handlerRequest(byte[] requestBytes) {
        log.info("handlerRequest Received data: {}", Arrays.toString(requestBytes));
        return "hello tcpRpc".getBytes();
    }

    public static void main(String[] args) {
        try {
            new VertxTcpServer().doStart(8080);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
