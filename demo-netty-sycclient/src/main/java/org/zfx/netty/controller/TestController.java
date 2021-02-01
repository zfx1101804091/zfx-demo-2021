package org.zfx.netty.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zfx.netty.client.VenusWebsocketClient;
import org.zfx.netty.model.VenusRequest;

import java.time.LocalDateTime;

@RestController
public class TestController {

    @GetMapping("ts")
    public String test1() {
        VenusRequest venusRequest = new VenusRequest();
        venusRequest.setUrl("ws://127.0.0.1:10010");
        venusRequest.setContent("hahaha");
        JSONObject object = VenusWebsocketClient.start("ws://127.0.0.1:10010", venusRequest);
        return object.toString();
    }
}
