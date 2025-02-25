package com.ayouok.fault.tolerant.impl;

import com.ayouok.fault.tolerant.TolerantStrategy;
import com.ayouok.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author ayouok
 * @create 2025-02-25-20:00
 * @desc 静默处理异常 - 容错策略
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
