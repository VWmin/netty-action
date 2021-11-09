package com.vwmin.nettyaction.server.resolver;

import com.vwmin.nettyaction.CustomProtocol;
import com.vwmin.nettyaction.MessageType;
import com.vwmin.nettyaction.NettySocketHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
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
        //保存客户端与 Channel 之间的关系
        NettySocketHolder.put(ctx.channel().remoteAddress()+"", (NioSocketChannel)ctx.channel());

        NettySocketHolder.getMAP().forEach((key, val) -> val.writeAndFlush(online(key)));
    }

    public static CustomProtocol online(String id){
        CustomProtocol online = new CustomProtocol();
        online.setType(MessageType.Login.value());
        online.setId("这是一个牛逼的ServerID");
        online.setTo(id);
        online.setContent(NettySocketHolder.getOnlineList());
        return online;
    }
}
