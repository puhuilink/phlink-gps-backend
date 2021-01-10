package com.xd.pre.modules.websocket;

import com.alibaba.fastjson.JSON;
import com.xd.pre.common.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WsController {
    @RequestMapping("/sendMsg")
    @ResponseBody
    public R sendMsg(String username, String msg) throws IOException {
        WebSocketSession webSocketSession = WsSessionManager.get(username);
        if (webSocketSession == null) {
            return R.error("用户登录已失效");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        WsMessage message = new WsMessage();
        message.setCode("msg");
        message.setMsg(msg);
        message.setTime(df.format(new Date()));
        webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(message)));
        return R.ok("消息发送成功");
    }
}
