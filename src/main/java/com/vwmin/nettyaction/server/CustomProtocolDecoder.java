package com.vwmin.nettyaction.server;

import com.google.gson.Gson;
import com.vwmin.nettyaction.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.internal.ObjectUtil;

import java.nio.charset.Charset;
import java.util.List;

public class CustomProtocolDecoder extends ByteToMessageDecoder {
    private final Charset charset;

    private final Gson gson;

    public CustomProtocolDecoder() {
        this(Charset.defaultCharset());
    }

    public CustomProtocolDecoder(Charset charset) {
        this.charset = ObjectUtil.checkNotNull(charset, "charset");
        this.gson = new Gson();

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        String jsonStr = new String(bytes, charset);

        CustomProtocol customProtocol = gson.fromJson(jsonStr, CustomProtocol.class);
        out.add(customProtocol);
    }

}