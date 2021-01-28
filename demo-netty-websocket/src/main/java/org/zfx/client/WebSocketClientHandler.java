package org.zfx.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class WebSocketClientHandler extends SimpleChannelInboundHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketClientHandler.class);
    WebSocketClientHandshaker handshaker;
    ChannelPromise handshakeFuture;

    private CountDownLatch lathc;
    private String result;

    public WebSocketClientHandler(CountDownLatch lathc) {
        this.lathc = lathc;
    }

    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    public WebSocketClientHandshaker getHandshaker() {
        return handshaker;
    }

    public void setHandshaker(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelPromise getHandshakeFuture() {
        return handshakeFuture;
    }

    public void setHandshakeFuture(ChannelPromise handshakeFuture) {
        this.handshakeFuture = handshakeFuture;
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("-----------------链接成功");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead0  " + this.handshaker.isHandshakeComplete());
        Channel ch = ctx.channel();
        FullHttpResponse response;
        if (!this.handshaker.isHandshakeComplete()) {
            try {
                response = (FullHttpResponse) msg;
                //握手协议返回，设置结束握手
                this.handshaker.finishHandshake(ch, response);
                //设置成功
                this.handshakeFuture.setSuccess();
                logger.info("WebSocket Client connected! response headers[sec-websocket-extensions]:{}" + response.headers());
            } catch (WebSocketHandshakeException var7) {
                FullHttpResponse res = (FullHttpResponse) msg;
                String errorMsg = String.format("WebSocket Client failed to connect,status:%s,reason:%s", res.status(), res.content().toString(CharsetUtil.UTF_8));
                this.handshakeFuture.setFailure(new Exception(errorMsg));
            }
        } else if (msg instanceof FullHttpResponse) {
            response = (FullHttpResponse) msg;
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        } else {
            WebSocketFrame frame = (WebSocketFrame) msg;
            if (frame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
//                this.listener.onMessage(textFrame.text());
                logger.info("从网关接收的数据： " + textFrame.text());

                result = textFrame.text();
                lathc.countDown();// 消息接收后释放同步锁，lathc是从Client加一传回来的
            }
        }
    }


    public void resetLatch(CountDownLatch lathc) {
        this.lathc = lathc;
    }

    public String getResult() {
        return result;
    }

    public static void createPlugin(Channel ch, Long sessionID) {
        JSONObject obj = JSONUtil.createObj();
        obj.putOpt("venus", "attach");
        obj.putOpt("plugin", "venus.plugin.videoroom");
        obj.putOpt("opaque_id", "videoroom-" + RandomUtil.randomString(12));
        obj.putOpt("transaction", "createPlugin-" + "videoroom" + RandomUtil.randomString(12));
        obj.putOpt("session_id", sessionID);
    }

    public static void createSession(Channel channel) {
        JSONObject message = JSONUtil.createObj();
        message.putOpt("venus", "create");
        message.putOpt("transaction", "createSession-" + RandomUtil.randomString(12));
        System.out.println("===========");
        channel.writeAndFlush(message);
    }
}
