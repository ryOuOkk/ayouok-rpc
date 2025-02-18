package com.ayouok.protocol.model;

import lombok.Data;

/**
 * @author ayouok
 * @create 2025-02-18-21:20
 * @desc 消息协议
 */
@Data
public class ProtocolMessage<T> {
    /**
     * 消息头
     */
    private Header header;
    /**
     * 消息体
     */
    private T body;

    @Data
    public static class Header {
        /**
         * 魔数,用于安全
         */
        private byte magic;
        /**
         * 版本号
         */
        private byte version;
        /**
         * 序列化器
         */
        private byte serializer;
        /**
         * 消息类型
         */
        private byte type;
        /**
         * 状态
         */
        private byte status;
        /**
         * 请求id
         */
        private long requestId;
        /**
         * 数据长度
         */
        private int bodyLength;
    }
}
