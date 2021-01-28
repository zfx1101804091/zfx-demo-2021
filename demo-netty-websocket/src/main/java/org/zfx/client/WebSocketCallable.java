package org.zfx.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class WebSocketCallable implements Callable {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketCallable.class);

    private String content;

    private Channel channel;

    private WebSocketCallable(){

    }

    public WebSocketCallable(Channel channel, String content){
        createSession(channel);
        this.channel = channel;
        this.content = content;
    }
    @Override
    public String call() throws Exception {

        TextWebSocketFrame frame = new TextWebSocketFrame(content);
        channel.writeAndFlush(frame).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    logger.info("callback is success");
                }else if (channelFuture.channel() == null){
                    logger.info("callback is failed" + channelFuture.cause().getMessage());
                    return;
                }
            }
        });
        return null;
    }

    public static void createSession(Channel channel) {
        JSONObject message = JSONUtil.createObj();
        message.putOpt("venus", "create");
        message.putOpt("transaction", "createSession-" + RandomUtil.randomString(12));
        System.out.println("===========");
        channel.writeAndFlush(message);
    }

}
