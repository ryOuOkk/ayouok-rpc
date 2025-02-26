package com.ayouok;

import com.ayouok.bootstarp.ProviderBootstrap;
import com.ayouok.config.RegistryConfig;
import com.ayouok.config.RpcConfig;
import com.ayouok.model.ServiceMetaInfo;
import com.ayouok.model.ServiceRegisterInfo;
import com.ayouok.registry.LocalRegistry;
import com.ayouok.registry.Registry;
import com.ayouok.registry.RegistryFactory;
import com.ayouok.server.HttpServer;
import com.ayouok.server.http.VertxHttpServer;
import com.ayouok.service.UserService;
import com.ayouok.service.impl.UserServiceImpl;

import java.util.ArrayList;

/**
 * Hello world!
 * @author ayouokk
 */
public class EasyProviderExample {
    public static void main(String[] args) throws Exception {
        ArrayList<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        //获取要注册的服务
        String serviceName = UserService.class.getName();
        ServiceRegisterInfo<UserService> userServiceServiceRegisterInfo = new ServiceRegisterInfo<>();
        userServiceServiceRegisterInfo.setServiceName(serviceName);
        userServiceServiceRegisterInfo.setImplClass(UserServiceImpl.class);
        serviceRegisterInfoList.add(userServiceServiceRegisterInfo);
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
