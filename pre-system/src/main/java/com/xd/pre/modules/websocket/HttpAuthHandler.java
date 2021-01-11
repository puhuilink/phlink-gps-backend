package com.xd.pre.modules.websocket;

import com.alibaba.fastjson.JSON;
import com.xd.pre.modules.security.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class HttpAuthHandler extends TextWebSocketHandler {

    public final static String MSG_HEART_CHECK = "heartCheck";
    public final static String MSG_MSG = "msg";
    public final static String MSG_CMD = "cmd";
    /**
     * 用户进入系统监听
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Object token = session.getAttributes().get("token");
        Object name = session.getAttributes().get("username");
        String username = JwtUtil.getUsernameFromToken(token.toString());
        if (name.toString().equals(username)) {
            // 用户信息保存
            WsSessionManager.add(username, session);
        }else {
            throw new RuntimeException("用户登录失效");
        }
    }

    /**
     * 接受消息事件
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        WsMessage msg = JSON.parseObject(payload, WsMessage.class);

        if(msg.getCode().equals(MSG_HEART_CHECK)) {
            session.sendMessage(new TextMessage(JSON.toJSONString(msg)));
        } else if(msg.getCode().equals(MSG_MSG)) {

        } else {
            System.out.println("消息不能被处理");
        }
    }

    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object name = session.getAttributes().get("username");
        if (name != null) {
            // 用户退出，移除缓存
            WsSessionManager.remove(name.toString());
        }
    }

}