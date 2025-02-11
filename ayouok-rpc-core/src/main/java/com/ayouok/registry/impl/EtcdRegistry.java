package com.ayouok.registry.impl;

import cn.hutool.json.JSONUtil;
import com.ayouok.config.RegistryConfig;
import com.ayouok.model.ServiceMetaInfo;
import com.ayouok.registry.Registry;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ayouokkk
 * @date 2025/2/11 21:01
 */
public class EtcdRegistry implements Registry {

    private Client client;

    private KV kvClient;

    /**
     * 根节点
     */
    private static final String ETCD_ROOT_PATH = "/rpc/";

    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder().endpoints(registryConfig.getAddress()).connectTimeout(Duration.ofMillis(registryConfig.getTimeout())).build();
        kvClient = client.getKVClient();
    }

    /**
     * 服务注册到注册中心
     *
     * @param serviceMetaInfo 服务元数据
     */
    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) {
        try {
            //获取leaseClient
            Lease leaseClient = client.getLeaseClient();
            //创建一个30秒的租约
            long id = leaseClient.grant(600).get().getID();
            //处理键值对
            String registerKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
            ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
            ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);
            //将键值对和租约关联起来
            PutOption putOption = PutOption.builder().withLeaseId(id).build();
            kvClient.put(key, value, putOption).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 服务取消注册
     *
     * @param serviceMetaInfo 服务元数据
     */
    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        //设置键
        ByteSequence key = ByteSequence.from(ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey(), StandardCharsets.UTF_8);
        kvClient.delete(key);
    }

    /**
     * 服务发现
     *
     * @param serviceKey 服务key
     * @return 服务元数据集合
     */
    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        try {
            String searchPrefix = ETCD_ROOT_PATH + serviceKey;
            GetOption option = GetOption.builder().isPrefix(true).build();
            List<KeyValue> kvs = kvClient.get(ByteSequence.from(searchPrefix, StandardCharsets.UTF_8), option).get().getKvs();
            return kvs.stream()
                    .map(kv -> {
                        String value = kv.getValue().toString(StandardCharsets.UTF_8);
                        return JSONUtil.toBean(value, ServiceMetaInfo.class);
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册中心销毁
     */
    @Override
    public void destroy() {
        if (kvClient != null) {
            kvClient.close();
        }
        if (client != null) {
            client.close();
        }
    }
}
