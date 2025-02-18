package com.ayouok.protocol.encoder;

import com.ayouok.protocol.model.ProtocolMessage;
import com.ayouok.protocol.protocolenum.ProtocolMessageSerializerEnum;
import com.ayouok.serializer.Serializer;
import com.ayouok.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author ayouok
 * @create 2025-02-18-22:13
 * @desc 协议消息编码器
 */
@Slf4j
public class ProtocolMessageEncoder {

    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws IOException {
        if (protocolMessage == null) {
            throw new IllegalArgumentException("protocolMessage cannot be null");
        }
        Buffer buffer = Buffer.buffer();
        try {
            //请求头信息编码
            buffer.appendByte(protocolMessage.getHeader().getMagic());
            buffer.appendByte(protocolMessage.getHeader().getVersion());
            buffer.appendByte(protocolMessage.getHeader().getSerializer());
            buffer.appendByte(protocolMessage.getHeader().getType());
            buffer.appendByte(protocolMessage.getHeader().getStatus());
            buffer.appendLong(protocolMessage.getHeader().getRequestId());
            //请求体信息编码
            //获取序列化枚举
            ProtocolMessageSerializerEnum enumByKey = ProtocolMessageSerializerEnum.getEnumByKey(protocolMessage.getHeader().getSerializer());
            //用序列化工厂获取序列化器
            assert enumByKey != null;
            Serializer serializer = SerializerFactory.getInstance(enumByKey.getValue());
            //序列化请求体
            byte[] bodyBytes = serializer.serialize(protocolMessage.getBody());
            buffer.appendInt(bodyBytes.length);
            buffer.appendBytes(bodyBytes);
        } catch (IOException e) {
            log.error("ProtocolMessageEncoder encode error");
            throw new RuntimeException(e);
        }
        return buffer;
    }
}
