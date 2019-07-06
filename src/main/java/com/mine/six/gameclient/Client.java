package com.mine.six.gameclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Aaron
 */
public class Client {
    private GameStatus gameStatus;
    public void start(){
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(SocketChannel.class)
                .handler(new ClientInitializer());
        try {
            Channel channel=bootstrap.connect("localhost",8000).sync().channel();
            while (true){
                channel.writeAndFlush(gameStatus);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus=gameStatus;
    }
}
