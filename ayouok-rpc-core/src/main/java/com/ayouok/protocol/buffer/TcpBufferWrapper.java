package com.ayouok.protocol.buffer;

import com.ayouok.protocol.constant.ProtocolConstant;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ayouok
 * @create 2025-02-19-21:57
 * @desc 装饰者模式, 对buffer进行包装, 穿上新装备
 */
@Slf4j
@Data
public class TcpBufferWrapper implements Handler<Buffer> {

    private final RecordParser recordParser;

    public TcpBufferWrapper(Handler<Buffer> handler) {
        recordParser = initRecordParser(handler);
    }

    private RecordParser initRecordParser(Handler<Buffer> handler) {
        //构造请求头的RecordParser
        RecordParser recordParser = RecordParser.newFixed(ProtocolConstant.MESSAGE_HEADER_LENGTH);
        recordParser.setOutput(new Handler<Buffer>() {
            int size = -1;
            //完整的读取请求
            Buffer resultBuffer = Buffer.buffer();

            @Override
            public void handle(Buffer buffer) {
                if (size == -1) {
                    //动态读取消息体长度,从第14个字节开始读取4个字节,getInt()默认是读4个字节
                    size = buffer.getInt(13);
                    recordParser.fixedSizeMode(size);
                    resultBuffer.appendBuffer(buffer);
                } else {
                    resultBuffer.appendBuffer(buffer);
                    log.info("VertxTcpServer Received data: {}", resultBuffer.toString());
                    //重置
                    recordParser.fixedSizeMode(ProtocolConstant.MESSAGE_HEADER_LENGTH);
                    size = -1;
                    resultBuffer = Buffer.buffer();
                }

            }
        });
        return recordParser;
    }

    @Override
    public void handle(Buffer buffer) {

    }
}
