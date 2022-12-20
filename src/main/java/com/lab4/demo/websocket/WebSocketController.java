package com.lab4.demo.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private WebSocketService webSocketService;

    public void sendNotification(String userId, String cons, String id, String maxCons) throws InterruptedException {
        Thread.sleep(500);
        webSocketService.processMsg(userId,cons, id, maxCons);
    }
}
