package com.vwmin.nettyaction;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettySocketHolder {
    private static final Map<String, NioSocketChannel> MAP = new ConcurrentHashMap<>(16);

    public static void put(String id, NioSocketChannel socketChannel) {
        MAP.put(id, socketChannel);
    }

    public static NioSocketChannel get(String id) {
        return MAP.get(id);
    }

    public static Map<String, NioSocketChannel> getMAP() {
        return MAP;
    }

    public static void remove(NioSocketChannel nioSocketChannel) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> MAP.remove(entry.getKey()));
    }


    public static boolean contain(String id){
        return MAP.containsKey(id);
    }
}