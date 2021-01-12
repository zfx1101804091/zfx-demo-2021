package com.zfx.learn.client;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SocketServer2 {
//   static ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            executorService.submit(()->{
//
//                // 要连接的服务端IP地址和端口
//                String host = "10.100.86.2";
//                int port = 5039;
//                // 与服务端建立连接
//                Socket socket = null;
//                try {
//                    socket = new Socket(host, port);
//                    // 建立连接后获得输出流
//                    OutputStream outputStream = socket.getOutputStream();
//                    String message="Type: McuNetWork\n" +
//                            "\n" +
//                            "\tAction: GETALL\n" +
//                            "\n" +
//                            "\tDevice: viosys-ds\n" +
//                            "\n" +
//                            "\tTransactionid:128955\n" +
//                            "\n" +
//                            "\tContent:\n" +
//                            "\n" +
//                            "\t<body>\n" +
//                            "        <start>0</start>\n" +
//                            "        <offset>1000</offset>\n" +
//                            "    </body>";
//                    socket.getOutputStream().write(message.getBytes("UTF-8"));
//                    // 必需关闭输出流
//                    socket.shutdownOutput();
//                    // 获取输出流
//                    InputStream inputStream = socket.getInputStream();
//                    byte[] bytes = new byte[1024];
//                    int len;
//                    StringBuilder sb = new StringBuilder();
//                    while ((len = inputStream.read(bytes)) != -1) {
//                        //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
//                        sb.append(new String(bytes, 0, len,"UTF-8"));
//                    }
//                    System.out.println("get message from server: " + sb);
//
//                    inputStream.close();
//                    outputStream.close();
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        executorService.shutdown();
//    }

    public static void main(String[] args) throws IOException {
        String host = "10.100.86.2";
        int port = 5039;
        String message1="Type: McuNetWork\n" +
                            "\n" +
                            "\tAction: GETALL\n" +
                            "\n" +
                            "\tDevice: viosys-ds\n" +
                            "\n" +
                            "\tTransactionid:128955\n" +
                            "\n" +
                            "\tContent:\n" +
                            "\n" +
                            "\t<body>\n" +
                            "        <start>0</start>\n" +
                            "        <offset>1000</offset>\n" +
                            "    </body>";

        String message = "Type: conference.basicinfo\n" +
                "\n" +
                "\tAction: GETALL\n" +
                "\n" +
                "\tDevice: viosys-ds\n" +
                "\n" +
                "\tTransactionid:880693\n" +
                "\n" +
                "\tContent:\n" +
                "\n" +
                "\t<body>\n" +
                "        <start>0</start>\n" +
                "        <offset>1000</offset>\n" +
                "    </body>";
        //与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);

        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        String uuid = UUID.randomUUID().toString();
        log.info("uuid: {}", uuid);
//        outputStream.write(uuid.getBytes());
        outputStream.write(message.getBytes());
        DataInputStream inputStream1 = new DataInputStream(socket.getInputStream());
        String content = "";
        System.out.println(content);
        while (true) {
            byte[] buff = new byte[1024];
            inputStream.read(buff);
            String buffer = new String(buff, "utf-8");
            content += buffer;
            log.info("info: {}", buff);
            File file = new File("json.xml");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
        }
    }
}
