package org.zfx.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zfx.client.WebSocketClient;

import java.net.URISyntaxException;

@RestController
public class TestController {

    @GetMapping("ts")
    public String ts() throws URISyntaxException, InterruptedException {
        JSONObject message = JSONUtil.createObj();
//        message.putOpt("request", "list");
        message.putOpt("request","create");
        message.putOpt("room",Long.valueOf(RandomUtil.randomNumbers(12)));
        message.putOpt("publishers",6);
        message.putOpt("permanent",true);
        message.putOpt("is_private",false);
        message.putOpt("bitrate",102400);
        message.putOpt("opus_fec",true);
        WebSocketClient webSocketClient = new WebSocketClient("ws://10.100.86.2:8188/gw");
        String result = webSocketClient.pushMsg(message.toString());
        return result;
    }

    @GetMapping("ts2")
    public String ts2() throws URISyntaxException, InterruptedException {
        JSONObject message = JSONUtil.createObj();
//        message.putOpt("request", "list");
        message.putOpt("request","list");

        WebSocketClient webSocketClient = new WebSocketClient("ws://10.100.86.2:8188/gw");
        String result = webSocketClient.pushMsg(message.toString());
        return result;
    }
}
