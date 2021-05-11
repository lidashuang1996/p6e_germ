package com.p6e.germ.stream.branch.server;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eGermStreamServerContext {

    /** 上下文缓存对象 */
    private static final Map<String, P6eGermStreamServerContext> CONTEXT_CACHE = new HashMap<>();

    private String id;
    private ChannelHandlerContext context;


    public String getId() {
        return id;
    }


    public void writeAndFlush(Object o) {
        if (context != null && context.channel() != null && context.isRemoved()) {
            context.writeAndFlush(o);
        }
    }
}
