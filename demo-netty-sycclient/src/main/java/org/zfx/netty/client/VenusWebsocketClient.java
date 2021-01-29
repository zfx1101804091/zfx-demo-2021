package org.zfx.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.zfx.netty.core.DefaultChannelFure;

public class VenusWebsocketClient {

    //客户端单例模式方便系统调用
    private static class SingletonHolder{
        static final VenusWebsocketClient instance = new VenusWebsocketClient();
    }
    public static VenusWebsocketClient getInstance(){
        return SingletonHolder.instance;
    }


    private static EventLoopGroup group=null;
    public static Bootstrap bootstrap=null;
    private DefaultChannelFure dcf ;

    static {
        group=new NioEventLoopGroup();
        bootstrap=new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(group)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new HttpClientCodec());
                        p.addLast(new HttpObjectAggregator(1024*1024*10));
                        p.addLast("websocketHandler", null);
                    }
                });
    }


}
