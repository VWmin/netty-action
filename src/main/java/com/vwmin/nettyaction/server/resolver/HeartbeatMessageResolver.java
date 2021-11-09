package com.vwmin.nettyaction.server.resolver;

import com.vwmin.nettyaction.CustomProtocol;
import com.vwmin.nettyaction.MessageType;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartbeatMessageResolver implements Resolver {

    private static final MessageType SUPPORT_MESSAGE_TYPE = MessageType.Heartbeat;
    private static final CustomProtocol PONG = pong();

    @Override
    public boolean support(CustomProtocol message) {
        return message.getType() == SUPPORT_MESSAGE_TYPE.value();
    }

    @Override
    public void resolve(ChannelHandlerContext ctx, CustomProtocol msg) {
        log.info("收到来自客户端的心跳 >>> {}", msg.getContent());
        ctx.channel().writeAndFlush(PONG);
    }

    private static CustomProtocol pong(){
        CustomProtocol pong = new CustomProtocol();
        pong.setId("这是一个牛逼的ServerID");
        pong.setType(MessageType.Heartbeat.value());
        pong.setContent("pong");
        return pong;
    }
}
