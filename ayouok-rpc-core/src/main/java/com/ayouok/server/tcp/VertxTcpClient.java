package com.ayouok.server.tcp;

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ayouokk
 */
@Slf4j
public class VertxTcpClient {

    public void start() {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        vertx.createNetClient().connect(8080, "localhost", result -> {
            if (result.succeeded()) {
                log.info("Connected to TCP server");
                io.vertx.core.net.NetSocket socket = result.result();
                // 发送数据
                socket.write("Hello, server!");
                // 接收响应
                socket.handler(buffer -> {
                    log.info("Received response from server:{}", buffer.toString());
                });
            } else {
                log.info("Failed to connect to TCP server");
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpClient().start();
    }
}
