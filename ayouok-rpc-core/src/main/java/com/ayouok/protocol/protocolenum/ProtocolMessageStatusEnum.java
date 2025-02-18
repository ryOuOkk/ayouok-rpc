package com.ayouok.protocol.protocolenum;

import lombok.Getter;

/**
 * @author ayouok
 * @create 2025-02-18-21:26
 * @desc 协议消息状态枚举
 */
@Getter
public enum ProtocolMessageStatusEnum {

    /**
     * 请求成功
     */
    OK("ok", 20),
    /**
     * 请求失败
     */
    BAD_REQUEST("badRequest", 40),
    /**
     * 响应失败
     */
    BAD_RESPONSE("badResponse", 50);

    /**
     * 状态信息
     */
    private final String text;

    /**
     * 状态码
     */
    private final int value;

    ProtocolMessageStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 状态码
     * @return ProtocolMessageStatusEnum
     */
    public static ProtocolMessageStatusEnum getEnumByValue(int value) {
        for (ProtocolMessageStatusEnum anEnum : ProtocolMessageStatusEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }
}
