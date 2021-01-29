package org.zfx;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zfx.client.TsWebsocketClient;

@SpringBootApplication
public class NettyClientApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        TsWebsocketClient client = new TsWebsocketClient();
//            client.connect("ws://49.234.18.41:8866","踩踩我是谁");
            client.connect("ws://127.0.0.1:7089","踩踩我是谁");
    }
}
