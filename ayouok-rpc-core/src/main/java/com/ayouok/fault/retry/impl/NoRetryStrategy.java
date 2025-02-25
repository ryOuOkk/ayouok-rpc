package com.ayouok.fault.retry.impl;

import com.ayouok.fault.retry.RetryStrategy;
import com.ayouok.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author ayouok
 * @create 2025-02-25-20:00
 * @desc 不重试策略
 */
public class NoRetryStrategy implements RetryStrategy {


    /**
     * 不重试策略
     * @param callable 响应
     * @return 返回响应
     * @throws Exception Exception
     */
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
