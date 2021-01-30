package org.zfx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zfx.netty.VenusWebsocketClient;

@RestController
public class TestController {

    @GetMapping("ts")
    public String get() {
        String result = VenusWebsocketClient.start("ws://127.0.0.1:10010", "踩踩我是谁啊");
        return result;
    }
}
