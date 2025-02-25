package com.ayouok.fault.tolerant.impl;

import com.ayouok.fault.tolerant.TolerantStrategy;
import com.ayouok.model.RpcResponse;

import java.util.Map;

/**
 * @author ayouok
 * @create 2025-02-25-20:00
 * @desc 快速失败 - 容错策略（立刻通知外层调用方）
 */
public class FailFastTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
