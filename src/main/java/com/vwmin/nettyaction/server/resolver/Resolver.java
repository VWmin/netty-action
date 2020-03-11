package com.vwmin.nettyaction.server.resolver;

import com.vwmin.nettyaction.CustomProtocol;
import io.netty.channel.ChannelHandlerContext;

public interface Resolver {
    boolean support(CustomProtocol message);
    void resolve(ChannelHandlerContext ctx, CustomProtocol message);
}
