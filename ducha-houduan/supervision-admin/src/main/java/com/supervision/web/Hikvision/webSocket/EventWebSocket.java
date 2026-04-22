package com.supervision.web.Hikvision.webSocket;


import com.alibaba.fastjson.JSON;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.framework.web.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")
public class EventWebSocket {

    private static final Logger logger = LoggerFactory.getLogger(EventWebSocket.class);

    private static final Map<Long, Session> USER_SESSION_MAP = new ConcurrentHashMap<>();

    private Long userId;

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        logger.info("🟢 WebSocket 请求到达，token={}", token);
        try {
            TokenService tokenService = SpringContextHolder.getBean(TokenService.class);
            LoginUser loginUser = tokenService.getLoginUserByToken(token);
            if (loginUser == null) {
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "Invalid token"));
                return;
            }
            this.userId = loginUser.getUserId();
            USER_SESSION_MAP.put(userId, session);
            logger.info("✅ 用户 {} 已连接 WebSocket", loginUser.getUsername());
        } catch (Exception e) {
            try { session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "Auth failed")); } catch (IOException ignored) {}
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if ("ping".equalsIgnoreCase(message)) {
            session.getAsyncRemote().sendText("pong");
        }
    }

    @OnClose
    public void onClose() {
        if (userId != null) {
            USER_SESSION_MAP.remove(userId);
            logger.info("❌ 用户 {} 已断开 WebSocket", userId);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("WebSocket 错误：{}", error.getMessage());
    }
    // 给某个用户发送事件
    // 门禁事件
    // EventWebSocket.sendToUser(userId, "doorGate", Map.of("gateId", 1, "status", "open"));
    // 车闸事件
    // EventWebSocket.sendToUser(userId, "carGate", Map.of("carId", "A12345", "status", "pass"));
    public static void sendToUser(Long userId, String type, Long deviceId, Object payload) {
        Session session = USER_SESSION_MAP.get(userId);
        if (session != null && session.isOpen()) {
            WebSocketMessage msg = new WebSocketMessage(type, deviceId, payload);
            session.getAsyncRemote().sendText(JSON.toJSONString(msg));
        }
    }

    // 广播给所有在线用户（线程安全）
    public static void broadcast(String type, Long deviceId, Object payload) {
        WebSocketMessage msg = new WebSocketMessage(type, deviceId, payload);
        String json = JSON.toJSONString(msg);

        for (Session session : USER_SESSION_MAP.values()) {
            if (session.isOpen()) {
                // 确保每个 session 内串行写入，避免 TEXT_FULL_WRITING
                synchronized (session) {
                    try {
                        session.getBasicRemote().sendText(json);
                    } catch (IOException e) {
                        logger.error("WebSocket 广播消息发送失败：{}", e.getMessage());
                    }
                }
            }
        }
    }

    // 消息结构
    private static class WebSocketMessage {

        private String type;

        private Long deviceId;

        private Object payload;

        public WebSocketMessage(String type, Long deviceId, Object payload) {
            this.type = type;
            this.deviceId = deviceId;
            this.payload = payload;
        }

        public String getType() { return type; }

        public Long getDeviceId() { return deviceId; }

        public Object getPayload() { return payload; }

    }
}
