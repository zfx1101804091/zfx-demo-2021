package org.zfx.netty.client;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
import org.zfx.netty.core.DefaultChannelFuture;
import org.zfx.netty.model.VenusRequest;
import org.zfx.netty.model.VenusResponse;

import java.net.URI;

public class VenusWebsocketClient {

    private static EventLoopGroup group;
    private static Bootstrap bootstrap;
    private static WebSocketClientHandshaker handshaker;


    public static JSONObject start(String url, VenusRequest request) {
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
//            future.channel().closeFuture().sync();

            DefaultChannelFuture dcf= new DefaultChannelFuture(request);
            VenusResponse response = dcf.get();
            return JSONUtil.parseObj(response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return null;
    }

}
