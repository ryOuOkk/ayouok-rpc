package com.ayouok.fault.tolerant.impl;

import com.ayouok.fault.tolerant.TolerantStrategy;
import com.ayouok.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author ayouok
 * @create 2025-02-25-20:00
 * @desc 转移到其他服务节点 - 容错策略
 */
@Slf4j
public class FailOverTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 可自行扩展，获取其他服务节点并调用
        return null;
    }
}
