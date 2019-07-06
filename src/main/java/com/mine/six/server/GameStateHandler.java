package com.mine.six.server;

import com.mine.six.gameclient.GameStatus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Aaron
 */
public class GameStateHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof GameStatus){
            GameStatus gameStatus= (GameStatus) o;
            Server.add(gameStatus.getSessionId(),gameStatus);
        }else {
            System.out.println(o.toString());
        }
    }
}
