package org.zfx;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zfx.client.WebSocketClient;

@SpringBootApplication
public class NettyDemoApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(NettyDemoApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {

//        String result = "";
//        WebSocketClient webSocketClient = new WebSocketClient("ws://localhost:8088");
//        result =webSocketClient.pushMsg("hello websocket"); // 返回结果
//        System.out.println("￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥"+ result);
    }
}
