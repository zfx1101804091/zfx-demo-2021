package org.zfx.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;

import java.net.URI;

public class VenusWebsocketClient {

    private static EventLoopGroup group;
    private static Bootstrap bootstrap;
    private static WebSocketClientHandshaker handshaker;

    public static String start(String url,String request) {
        try {
            URI uri = URI.create(url);
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                    URI.create(url),
                    WebSocketVersion.V13,
                    "",
                    true,
                    new DefaultHttpHeaders(),
                    1024 * 1024 * 10
            );
            bootstrap.channel(NioSocketChannel.class)
                    .group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new HttpClientCodec());
                            p.addLast(new HttpObjectAggregator(1024 * 1024 * 10));
                            p.addLast("websocketHandler", new VenusWebsocketHandler(handshaker));
                        }
                    });
            ChannelFuture future = bootstrap.connect(uri.getHost(), uri.getPort()).sync();
            future.channel().closeFuture().sync();
            return future.channel().attr(AttributeKey.valueOf("res")).get().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return null;
    }

}
