package com.ayouok.fault.retry;

import com.ayouok.model.RpcResponse;
import java.util.concurrent.Callable;

/**
 * @author ayouok
 * @create 2025-02-25-20:00
 * @desc 重试策略接口
 */
public interface RetryStrategy {
    /**
     * 执行重试
     * @param callable callable
     * @return RpcResponse
     * @throws Exception Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;

}
