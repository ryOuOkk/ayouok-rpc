package com.ayouok.registry.impl;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import com.ayouok.config.RegistryConfig;
import com.ayouok.model.ServiceMetaInfo;
import com.ayouok.registry.Registry;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ayouokkk
 * @date 2025/2/11 21:01
 */
@Slf4j
public class EtcdRegistry implements Registry {

    private Client client;

    private KV kvClient;

    /**
     * 本机注册的节点 key 集合（用于维护续期）
     */
    private final Set<String> LOCAL_REGISTER_NODE_KEY_SET = new HashSet<>();


    /**
     * 根节点
     */
    private static final String ETCD_ROOT_PATH = "/rpc/";

    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder().endpoints(registryConfig.getAddress()).connectTimeout(Duration.ofMillis(registryConfig.getTimeout())).build();
        kvClient = client.getKVClient();
        heartbeat();
        log.info("etcdRegister init success");
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
            long id = leaseClient.grant(30).get().getID();
            //处理键值对
            String registerKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
            ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
            ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);
            //将键值对和租约关联起来
            PutOption putOption = PutOption.builder().withLeaseId(id).build();
            kvClient.put(key, value, putOption).get();
            //将租约ID添加到本地集合中
            LOCAL_REGISTER_NODE_KEY_SET.add(registerKey);
            log.info("服务注册成功：{}", JSONUtil.toJsonStr(serviceMetaInfo));
        } catch (Exception e) {
            log.error("服务注册失败：{}", serviceMetaInfo);
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
        LOCAL_REGISTER_NODE_KEY_SET.remove(ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey());
        log.info("服务取消注册成功：{}", JSONUtil.toJsonStr(serviceMetaInfo));
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
            List<ServiceMetaInfo> serviceMetaInfoList =  kvs.stream()
                    .map(kv -> {
                        String value = kv.getValue().toString(StandardCharsets.UTF_8);
                        return JSONUtil.toBean(value, ServiceMetaInfo.class);
                    }).collect(Collectors.toList());
            log.info("服务发现：{}", JSONUtil.toJsonStr(serviceMetaInfoList));
            return serviceMetaInfoList;
        } catch (Exception e) {
            log.error("服务发现失败：{}", serviceKey);
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
        CronUtil.stop();
        log.info("etcdRegister destroy success");
    }

    /**
     * 心跳检测
     */
    @Override
    public void heartbeat() {
        CronUtil.schedule("*/10 * * * * *", (Task) () -> {
            for (String nodeKey : LOCAL_REGISTER_NODE_KEY_SET) {
                try {
                    //获取keyValue集合
                    List<KeyValue> keyValueList = kvClient.get(ByteSequence.from(nodeKey, StandardCharsets.UTF_8)).get().getKvs();
                    //如果keyValue集合是空的,说明节点已过期
                    if (keyValueList.isEmpty()) {
                        continue;
                    }
                    //获取keyValue
                    KeyValue keyValue = keyValueList.get(0);
                    //获取value
                    String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                    //重新注册节点
                    ServiceMetaInfo serviceMetaInfo = JSONUtil.toBean(value, ServiceMetaInfo.class);
                    register(serviceMetaInfo);
                    log.info("节点续签成功：{}", nodeKey);
                } catch (Exception e) {
                    log.error("节点续签失败：{}", nodeKey);
                    throw new RuntimeException(nodeKey + "续签失败", e);
                }
            }
        });
        CronUtil.setMatchSecond(true);
        CronUtil.start();
        log.info("心跳检测启动");
    }
}
