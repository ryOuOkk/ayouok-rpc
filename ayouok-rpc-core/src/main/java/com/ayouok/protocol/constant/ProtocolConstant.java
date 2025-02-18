package com.ayouok.protocol.constant;

/**
 * @author ayouok
 * @create 2025-02-18-21:26
 * @desc 协议常量
 */
public interface ProtocolConstant {
    /**
     * 消息头长度
     */
    int MESSAGE_HEADER_LENGTH = 17;
    /**
     * 协议魔数
     */
    byte PROTOCOL_MAGIC = 0x1;
    /**
     * 协议版本
     */
    byte PROTOCOL_VERSION = 0x1;
}
