package org.zfx.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.SocketAddress;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public class TsWebsocketClient {

    public String connect(String url, String content) {
        URI uri = URI.create(url);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
//                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
//                    .remoteAddress(uri.getHost(), uri.getPort())
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new HttpClientCodec());
                            channel.pipeline().addLast(new HttpObjectAggregator(1024*1024*10));
                            channel.pipeline().addLast(new IdleStateHandler(20,10,0));
                            channel.pipeline().addLast("tsClientHander",new TsWebsocketClientHandler());
                        }
                    });
            //异步连接
            final ChannelFuture future = bootstrap.connect(uri.getHost(), uri.getPort()).sync();
            Channel channel = future.channel();

            TsWebsocketClientHandler handler = (TsWebsocketClientHandler) channel.pipeline().get("tsClientHander");
            WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                    uri,
                    WebSocketVersion.V13,
                    (String) null,
                    true,
                    new DefaultHttpHeaders(),
                    65535*5);

            handler.setHandshaker(handshaker);
            handshaker.handshake(channel);
            //阻塞等待是否握手成功
            handler.handshakeFuture().sync();
            System.out.println("握手成功");
            //给服务端发送的内容，如果客户端与服务端连接成功后，可以多次掉用这个方法发送消息
            sengMessage(channel);
            //绑定端口后,后面future.channel().closeFuture().sync();主线程变为wait状态了
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
        return null;
    }
    public static void sengMessage(Channel channel){
        //发送的内容，是一个文本格式的内容
        String putMessage="你好，我是客户端";
        TextWebSocketFrame frame = new TextWebSocketFrame(putMessage);
        channel.writeAndFlush(frame).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("消息发送成功，发送的消息是："+putMessage);
                } else {
                    System.out.println("消息发送失败 " + channelFuture.cause().getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        TsWebsocketClient client = new TsWebsocketClient();
//            client.connect("ws://49.234.18.41:8866","踩踩我是谁");
        String content = "现在北京时间："+LocalDateTime.now().toString();
        client.connect("ws://127.0.0.1:10011",content);
    }
}
