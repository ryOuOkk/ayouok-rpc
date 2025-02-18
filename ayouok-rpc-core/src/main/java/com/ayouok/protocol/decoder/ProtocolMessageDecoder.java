package com.ayouok.protocol.decoder;

import com.ayouok.model.RpcRequest;
import com.ayouok.model.RpcResponse;
import com.ayouok.protocol.constant.ProtocolConstant;
import com.ayouok.protocol.model.ProtocolMessage;
import com.ayouok.protocol.protocolenum.ProtocolMessageSerializerEnum;
import com.ayouok.protocol.protocolenum.ProtocolMessageTypeEnum;
import com.ayouok.serializer.Serializer;
import com.ayouok.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


/**
 * @author ayouok
 * @create 2025-02-18-22:13
 * @desc 协议消息解码器
 */
@Slf4j
public class ProtocolMessageDecoder {

    /**
     * 解码
     *
     * @param buffer buffer
     * @return ProtocolMessage
     * @throws IOException IOException
     */
    public static ProtocolMessage<?> decode(Buffer buffer) throws IOException {
        // 分别从指定位置读出 Buffer
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        byte magic = buffer.getByte(0);
        // 校验魔数
        if (magic != ProtocolConstant.PROTOCOL_MAGIC) {
            throw new RuntimeException("消息 magic 非法");
        }
        //默认读一个字节
        header.setMagic(magic);
        header.setVersion(buffer.getByte(1));
        header.setSerializer(buffer.getByte(2));
        header.setType(buffer.getByte(3));
        header.setStatus(buffer.getByte(4));
        header.setRequestId(buffer.getLong(5));
        header.setBodyLength(buffer.getInt(13));
        // 解决粘包问题，只读指定长度的数据
        byte[] bodyBytes = buffer.getBytes(17, 17 + header.getBodyLength());
        // 解析消息体
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if (serializerEnum == null) {
            log.error("ProtocolMessageDecoder 序列化消息的协议不存在");
            throw new RuntimeException("序列化消息的协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        ProtocolMessageTypeEnum messageTypeEnum = ProtocolMessageTypeEnum.getEnumByKey(header.getType());
        if (messageTypeEnum == null) {
            log.error("ProtocolMessageDecoder 序列化消息的类型不存在");
            throw new RuntimeException("序列化消息的类型不存在");
        }
        return switch (messageTypeEnum) {
            case REQUEST -> {
                RpcRequest request = serializer.deserialize(bodyBytes, RpcRequest.class);
                yield new ProtocolMessage<>(header, request);
            }
            case RESPONSE -> {
                RpcResponse response = serializer.deserialize(bodyBytes, RpcResponse.class);
                yield new ProtocolMessage<>(header, response);
            }
            default -> throw new RuntimeException("暂不支持该消息类型");
        };
    }

}
