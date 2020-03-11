package com.vwmin.nettyaction.server;

import com.google.gson.Gson;
import com.vwmin.nettyaction.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.ObjectUtil;

import java.nio.charset.Charset;

public class CustomProtocolEncoder extends MessageToByteEncoder<CustomProtocol> {
    private final Charset charset;
    private final Gson gson;

    public CustomProtocolEncoder() {
        this(Charset.defaultCharset());
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {
        String jsonStr = gson.toJson(msg)+"\n";
        out.writeBytes(jsonStr.getBytes(charset));
    }

    public CustomProtocolEncoder(Charset charset) {
        this.charset = ObjectUtil.checkNotNull(charset, "charset");
        this.gson = new Gson();
    }
}
