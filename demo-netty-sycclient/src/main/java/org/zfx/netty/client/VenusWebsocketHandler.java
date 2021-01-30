package org.zfx.netty.client;

import cn.hutool.json.JSONUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.zfx.netty.core.DefaultChannelFuture;
import org.zfx.netty.model.VenusResponse;

@Slf4j
public class VenusWebsocketHandler extends SimpleChannelInboundHandler<Object> {

    WebSocketClientHandshaker handshaker;
    ChannelPromise handshakeFuture;//握手结果
    private Object VenusResponse;

    public VenusWebsocketHandler(WebSocketClientHandshaker handshaker) {
        this.handshaker=handshaker;
    }

    //ChannelHandler添加到实际上下文中准备处理事件。
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.handshakeFuture = ctx.newPromise();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       log.info("客户端连接了 [{}] "+ctx.channel().remoteAddress().toString().substring(1));
       handshaker.handshake(ctx.channel());
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
                ctx.channel().writeAndFlush(new TextWebSocketFrame("你好啊啊啊啊"));
                log.info("WebSocket Client connected! response headers[sec-websocket-extensions]:{}" + response.headers());
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
                log.info("从网关接收的数据： " + textFrame.text());
                DefaultChannelFuture.recive(JSONUtil.toBean(textFrame.text(), VenusResponse.class));
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        log.error("出现异常。。。。");
    }


}
