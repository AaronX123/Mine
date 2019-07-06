package com.mine.six.gameclient;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import org.apache.commons.lang3.StringUtils;

/**
 *  @author 肖又铭
 *  @author 李康
 */
public class ClientStateHandler extends SimpleChannelInboundHandler{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println(o.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接建立成功与主机:" +ctx.channel().remoteAddress());
    }
}
