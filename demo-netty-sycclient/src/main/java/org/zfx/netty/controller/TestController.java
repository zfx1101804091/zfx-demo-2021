package org.zfx.netty.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zfx.netty.client.VenusWebsocketClient;

import java.time.LocalDateTime;

@RestController
public class TestController {

    @GetMapping("ts")
    public void test1() {
        VenusWebsocketClient.start("ws://127.0.0.1:10010", LocalDateTime.now().toString());
    }
}
