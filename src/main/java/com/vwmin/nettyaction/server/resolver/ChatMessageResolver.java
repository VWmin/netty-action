package com.vwmin.nettyaction.server.resolver;

import com.vwmin.nettyaction.CustomProtocol;
import com.vwmin.nettyaction.MessageType;
import com.vwmin.nettyaction.NettySocketHolder;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatMessageResolver implements Resolver {
    private static final MessageType SUPPORT_MESSAGE_TYPE = MessageType.Chat;

    @Override
    public boolean support(CustomProtocol message) {
        return message.getType() == SUPPORT_MESSAGE_TYPE.value();
    }

    @Override
    public void resolve(ChannelHandlerContext ctx, CustomProtocol msg) {
        log.info("收到从{}发往{}的消息 >>> {}", msg.getId(), msg.getTo(), msg.getContent());
        if (NettySocketHolder.contain(msg.getTo())){
            NettySocketHolder.get(msg.getTo()).writeAndFlush(msg)
                    .addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
    }
}
