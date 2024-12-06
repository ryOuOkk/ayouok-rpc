package com.ayouok;

import com.ayouok.registry.LocalRegistry;
import com.ayouok.server.VertxHttpServer;
import com.ayouok.service.UserService;
import com.ayouok.service.impl.UserServiceImpl;

/**
 * Hello world!
 * @author ayouokk
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        VertxHttpServer server = new VertxHttpServer();
        server.doStart(8080);
    }
}
