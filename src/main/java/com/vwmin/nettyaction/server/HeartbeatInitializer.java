package com.vwmin.nettyaction.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

public class HeartbeatInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) {
        ch.pipeline()
                //五秒没有收到消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(10, 0, 0))
                .addLast(new LineBasedFrameDecoder(2048))
                .addLast(new CustomProtocolEncoder(CharsetUtil.UTF_8))
                .addLast(new CustomProtocolDecoder(CharsetUtil.UTF_8))
                .addLast(new HeartBeatSimpleHandle());
    }
}