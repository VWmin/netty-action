package com.vwmin.nettyaction.server.resolver;

import com.vwmin.nettyaction.CustomProtocol;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageResolverFactory {
    private static final MessageResolverFactory FACTORY = new MessageResolverFactory();
    private static final List<Resolver> RESOLVERS = new CopyOnWriteArrayList<>();

    private MessageResolverFactory() {
    }

    public static MessageResolverFactory getFactory() {
        return FACTORY;
    }

    public void registerResolver(Resolver resolver) {
        RESOLVERS.add(resolver);
    }

    public Resolver getMessageResolver(CustomProtocol message) {
        for (Resolver resolver : RESOLVERS) {
            if (resolver.support(message)) {
                return resolver;
            }
        }
        throw new RuntimeException("cannot find resolver, message type: " + message.getType());
    }
}
