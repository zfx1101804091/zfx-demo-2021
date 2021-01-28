package org.zfx.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebSocketClient {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);
    private static ExecutorService executor = Executors.newCachedThreadPool();

    private static String uri;
    private static CountDownLatch latch;
    private static ClientInitializer clientInitializer;

    private WebSocketClient() {
    }

    public WebSocketClient(String uri) {
        this.uri = uri;
        latch = new CountDownLatch(0);
        clientInitializer = new ClientInitializer(latch);
    }

    private static class SingletonHolder {
        static final WebSocketClient instance = new WebSocketClient();
    }

    public static WebSocketClient getInstance() {
        return SingletonHolder.instance;
    }


    public static String pushMsg(String content) throws URISyntaxException, InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();
        boot.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .group(group)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer(latch));
        URI websocketURI = new URI(uri);
        HttpHeaders httpHeaders = new DefaultHttpHeaders();
        //进行握手
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                websocketURI,
                WebSocketVersion.V13,
                "venus-protocol",
                true,
                httpHeaders);
        logger.info("connect to server....");
        final Channel channel = boot.connect(websocketURI.getHost(), websocketURI.getPort()).sync().channel();
        WebSocketClientHandler handler = (WebSocketClientHandler) channel.pipeline().get("websocketHandler");
        handler.setHandshaker(handshaker);
        handshaker.handshake(channel);
        //阻塞等待是否握手成功
        handler.handshakeFuture().sync();

        JSONObject sessionMsg = JSONUtil.createObj();
        sessionMsg.putOpt("venus", "create");
        sessionMsg.putOpt("transaction", "createSession-" + RandomUtil.randomString(12));
        String result1 = sendMsg(channel, handler, sessionMsg.toString());
        JSONObject parseObj = JSONUtil.parseObj(result1);
        Long sessionID = parseObj.getJSONObject("data").getLong("id");
        if (parseObj.getStr("venus").equals("success")) {
            if (parseObj.getStr("transaction").startsWith("createSession")) {
                JSONObject obj = JSONUtil.createObj();
                obj.putOpt("venus", "attach");
                obj.putOpt("plugin", "venus.plugin.videoroom");
                obj.putOpt("opaque_id", "videoroom-" + RandomUtil.randomString(12));
                obj.putOpt("transaction", "createPlugin-" + "videoroom" + RandomUtil.randomString(12));
                obj.putOpt("session_id", sessionID);

                String result2 = sendMsg(channel, handler, obj.toString());
                JSONObject parseObj2 = JSONUtil.parseObj(result2);
                Long handleId = parseObj2.getJSONObject("data").getLong("id");
                if (parseObj2.getStr("transaction").startsWith("createPlugin")) {
                    cn.hutool.json.JSONObject constroMsg = JSONUtil.createObj();
                    constroMsg.put("venus", "message");
                    constroMsg.put("body", JSONUtil.parseObj(content));
                    constroMsg.put("transaction", "videoroom-createRoom-" + RandomUtil.randomString(12));
                    constroMsg.put("session_id", sessionID);
                    constroMsg.put("handle_id", handleId);

                    String result3 = sendMsg(channel, handler, constroMsg.toString());
                    JSONObject parseObj3 = JSONUtil.parseObj(result3);

                    if (parseObj3.getStr("transaction").startsWith("videoroom-createRoom")) {
                        return result3;
                    }
                }
            }

        }
        return null;
    }

    public static String sendMsg(Channel channel, WebSocketClientHandler handler, String content) throws InterruptedException {
        logger.info("send msg:" + content);
        //发起线程发送消息
        executor.submit(new WebSocketCallable(channel, content));
        latch = new CountDownLatch(1);//发送数据控制门闩加一
        clientInitializer.setHandler(handler);
        clientInitializer.resetLathc(latch);
        //等待，当websocket服务端返回数据时唤醒屏障，并返回结果
        latch.await();
        return clientInitializer.getServerResult();

    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        String result = "";
        JSONObject message = JSONUtil.createObj();
        message.putOpt("venus", "create");
        message.putOpt("transaction", "createSession-" + RandomUtil.randomString(12));

        WebSocketClient webSocketClient = new WebSocketClient("ws://10.100.86.2:8188/gw");
        result = webSocketClient.pushMsg(message.toString()); // 返回结果
        System.out.println("￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥" + result);
    }
}
