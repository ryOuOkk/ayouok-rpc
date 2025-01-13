package com.ayouok.config;

/**
 * @author dxn
 * @date 2024年12月20日 15:23
 * @apiNote
 */
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "ayouok-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 是否开启mock
     */
    private boolean mock = false;

}