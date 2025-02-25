package com.ayouok.fault.retry.impl;

import com.ayouok.fault.retry.RetryStrategy;
import com.ayouok.model.RpcResponse;
import com.github.rholder.retry.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author ayouok
 * @create 2025-02-25-20:00
 * @desc 固定时间间隔 - 重试策略
 */
@Slf4j
public class FixedIntervalRetryStrategy implements RetryStrategy {

    /**
     * 重试
     * @param callable callable
     * @return RpcResponse
     */
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                //重试异常类型
                .retryIfExceptionOfType(Exception.class)
                //重试等待策略
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS))
                //重试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                //重试监听器
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("重试次数 {}", attempt.getAttemptNumber());
                    }
                })
                .build();
        return retryer.call(callable);
    }

}
