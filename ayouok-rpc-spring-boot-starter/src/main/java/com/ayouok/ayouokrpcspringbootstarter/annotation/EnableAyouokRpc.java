package com.ayouok.ayouokrpcspringbootstarter.annotation;

import com.ayouok.ayouokrpcspringbootstarter.bootstrap.RpcConsumerBootstrap;
import com.ayouok.ayouokrpcspringbootstarter.bootstrap.RpcInitBootstrap;
import com.ayouok.ayouokrpcspringbootstarter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ayouok
 * @create 2025-02-26-20:21
 * @Desc 开启rpc注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableAyouokRpc {

    /**
     * 需要启动server
     * @return 是否
     */
    boolean needServer() default true;

}
