package com.supervision.web.Hikvision.webSocket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 启用 WebSocket 支持
 */
@Configuration
public class WebSocketConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        logger.info("✅ WebSocket ServerEndpointExporter 已加载");
        return new ServerEndpointExporter();
    }
}
