package com.ayouok.server;

/**
 * @author ayouok
 * @create 2024-12-06-15:42
 */
public interface HttpServer {

    /**
     * 启动服务
     * @param port 端口
     * @throws Exception
     */
    void doStart(int port) throws Exception ;
}
