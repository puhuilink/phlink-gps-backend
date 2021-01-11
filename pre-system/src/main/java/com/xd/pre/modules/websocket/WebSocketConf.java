package com.xd.pre.modules.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConf implements WebSocketConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;
    @Autowired
    private HttpAuthHandler httpAuthHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(httpAuthHandler, "ws")
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");

    }

}