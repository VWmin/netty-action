package com.vwmin.nettyaction.server;

import com.vwmin.nettyaction.CustomProtocol;
import com.vwmin.nettyaction.NettySocketHolder;
import com.vwmin.nettyaction.server.resolver.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.vwmin.nettyaction.server.resolver.LoginMessageResolver.online;


@Slf4j
public class HeartBeatSimpleHandle extends SimpleChannelInboundHandler<CustomProtocol> {

    private final MessageResolverFactory resolverFactory = MessageResolverFactory.getFactory();

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        NettySocketHolder.remove((NioSocketChannel) ctx.channel());

        NettySocketHolder.getMAP().forEach((key, val)->val.writeAndFlush(online(key)));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("远程地址'{}'连接建立成功", ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;

            if (idleStateEvent.state() == IdleState.READER_IDLE){
                log.info("已经10秒没有收到来自心跳包，将释放链接`{}`", ctx.channel().remoteAddress());
                ctx.close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) {
        Resolver resolver = resolverFactory.getMessageResolver(msg);
        resolver.resolve(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException){
            log.info("对方主机关闭了连接或连接发生意外");
            return;
        }
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        resolverFactory.registerResolver(new ChatMessageResolver());
        resolverFactory.registerResolver(new HeartbeatMessageResolver());
        resolverFactory.registerResolver(new LoginMessageResolver());
        resolverFactory.registerResolver(new ResponseMessageResolver());
        super.channelRegistered(ctx);
    }
}
