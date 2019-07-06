package com.mine.six.server;

import com.mine.six.gameclient.GameStatus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * @author Aaron
 */
public class GameStateHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof GameStatus){
            GameStatus gameStatus= (GameStatus) o;
            Server.add(gameStatus.getSessionId(),gameStatus);
            channelHandlerContext.writeAndFlush("用户会话id为"+((GameStatus) o).getSessionId());
        }else {
            System.out.println(o.toString());
        }
        channelHandlerContext.writeAndFlush("收到来自"+channelHandlerContext.channel().remoteAddress()+"的消息"+o.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户机: "+ctx.channel().remoteAddress()+" 已上线！");
    }
}
