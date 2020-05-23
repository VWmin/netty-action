package com.vwmin.nettyaction.server.resolver;

import com.vwmin.nettyaction.CustomProtocol;
import com.vwmin.nettyaction.MessageType;
import com.vwmin.nettyaction.NettySocketHolder;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/5/18 16:22
 */
@Slf4j
public class ResponseMessageResolver implements Resolver{

    private static final MessageType SUPPORT_MESSAGE_TYPE = MessageType.Response;


    @Override
    public boolean support(CustomProtocol message) {
        return message.getType() == SUPPORT_MESSAGE_TYPE.value();
    }

    @Override
    public void resolve(ChannelHandlerContext ctx, CustomProtocol message) {
        log.info("收到来自{}的执行响应，正在传回{}", message.getId(), message.getTo());
        NettySocketHolder.get(message.getTo()).writeAndFlush(message);
    }
}
