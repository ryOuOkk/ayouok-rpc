package com.ayouok.fault.tolerant;


import com.ayouok.model.RpcResponse;

import java.util.Map;

/**
 * @author ayouok
 * @create 2025-02-25-20:00
 * @desc 容错策略
 */
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @param e       异常
     * @return RpcResponse
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
