package com.ayouok.protocol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ayouok
 * @create 2025-02-18-21:20
 * @desc 消息协议
 * 请求头 17个字节长度
 *  -魔数 1个字节长度
 *  -版本号 1个字节长度
 *  -序列化器 1个字节长度
 *  -消息类型 1个字节长度
 *  -状态 1个字节长度
 *  -请求id 8个字节长度
 *  -数据长度 4个字节长度
 */
@AllArgsConstructor
@NoArgsConstructor
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
