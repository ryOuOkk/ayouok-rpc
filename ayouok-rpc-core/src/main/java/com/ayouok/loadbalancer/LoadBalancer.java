package com.ayouok.loadbalancer;

import com.ayouok.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @author dxn
 * @date 2025年02月25日 17:03
 * @apiNote 负载均衡接口
 */
public interface LoadBalancer {

    /**
     * 根据请求参数和服务元信息列表选择一个服务元信息
     * @param requestParams 请求参数
     * @param serviceMetaInfoList 服务元信息列表
     * @return 服务
     */
    ServiceMetaInfo select(Map<String,Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
