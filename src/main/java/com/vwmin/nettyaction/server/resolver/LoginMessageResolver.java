package com.vwmin.nettyaction.server.resolver;

import com.vwmin.nettyaction.CustomProtocol;
import com.vwmin.nettyaction.MessageType;
import com.vwmin.nettyaction.NettySocketHolder;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginMessageResolver implements Resolver {
    private static final MessageType SUPPORT_MESSAGE_TYPE = MessageType.Login;

    @Override
    public boolean support(CustomProtocol message) {
        return message.getType() == SUPPORT_MESSAGE_TYPE.value();
    }

    @Override
    public void resolve(ChannelHandlerContext ctx, CustomProtocol message) {
        log.info("收到来自客户端'{}'的在线查询", message.getId());
        ctx.writeAndFlush(online());
    }

    private static CustomProtocol online(){
        CustomProtocol online = new CustomProtocol();
        online.setType(MessageType.Login.value());
        online.setId("这是一个牛逼的ServerID");
        online.setContent(NettySocketHolder.getOnlineList());
        return online;
    }
}
