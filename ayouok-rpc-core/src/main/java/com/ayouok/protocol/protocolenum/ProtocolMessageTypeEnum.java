package com.ayouok.protocol.protocolenum;

import lombok.Getter;

/**
 * @author ayouok
 * @create 2025-02-18-21:26
 * @desc 协议消息类型枚举
 */
@Getter
public enum ProtocolMessageTypeEnum {

    /**
     * 请求
     */
    REQUEST(0),
    /**
     * 响应
     */
    RESPONSE(1),
    /**
     * 心跳
     */
    HEART_BEAT(2),
    /**
     * 其他
     */
    OTHERS(3);

    private final int key;

    ProtocolMessageTypeEnum(int key) {
        this.key = key;
    }

    /**
     * 根据 key 获取枚举
     *
     * @param key key
     * @return ProtocolMessageTypeEnum
     */
    public static ProtocolMessageTypeEnum getEnumByKey(int key) {
        for (ProtocolMessageTypeEnum anEnum : ProtocolMessageTypeEnum.values()) {
            if (anEnum.key == key) {
                return anEnum;
            }
        }
        return null;
    }
}
