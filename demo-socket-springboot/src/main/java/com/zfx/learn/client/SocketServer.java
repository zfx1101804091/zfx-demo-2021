package com.zfx.learn.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@Data
@NoArgsConstructor
@PropertySource("classpath:socket.properties")
public class SocketServer {

    @Value("${port}")
    private Integer port;

    @Value("${keepAlive}")
    private boolean keepAlive;

    private boolean started;
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 测试服务端启动
     *
     * @param args
     */
    public static void main(String[] args) {
        new SocketServer().start(8081);
    }

    public void start() {
        start(null);
    }


    public void start(Integer port) {
        try {
            log.info("port: 配置端口{}, 主动指定启动端口{}", this.port, port);
            serverSocket=new ServerSocket(port == null ? this.port : port);
            started=true;
            log.info("Socket服务已启动，占用端口： {}", serverSocket.getLocalPort());

        } catch (IOException e) {
            log.error("端口冲突,异常信息：{}", e);
            System.exit(0);
        }

        while (true){
            try {
                Socket socket = serverSocket.accept();
                socket.setKeepAlive(keepAlive);
                socket.setSoTimeout(5000);

                executorService.submit(()->{

                });

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}
