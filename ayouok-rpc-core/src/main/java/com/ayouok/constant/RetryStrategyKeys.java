package com.ayouok.constant;

import com.ayouok.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author ayouok
 * @create 2025-02-25-20:00
 * @desc 重试策略接口
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";
    /**
     * 固定间隔重试
     */
    String FIXED_INTERVAL = "fixedInterval";

}
