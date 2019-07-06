package com.mine.six.server;

import com.mine.six.gameclient.GameStatus;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.lang3.StringUtils;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaoyouming
 */
public class Server {
    private static ConcurrentHashMap hashMap=new ConcurrentHashMap();
    private int port;
    private SocketChannel socketChannel;
    private ServerBootstrap bootstrap;
    Server(){
    }
    public void start(){
    EventLoopGroup boss=new NioEventLoopGroup();
    EventLoopGroup worker=new NioEventLoopGroup();
    bootstrap=new ServerBootstrap();
    bootstrap.group(boss,worker).channel(NioServerSocketChannel.class)
           .childHandler(new ServerInitializer());
        try {
            ChannelFuture channelFuture=bootstrap.bind(10800).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
    public static void add(String sessionId, GameStatus gameStatus){
        if (StringUtils.isNotEmpty(sessionId)&&gameStatus!=null) {
            hashMap.put(sessionId, gameStatus);
        }
    }
    public static GameStatus getGameStatus(String sessionId){
        if (StringUtils.isNotEmpty(sessionId)){
            return (GameStatus) hashMap.get(sessionId);
        }
        return null;
    }
    public static void main(String[] args) {
        Server server=new Server();
        server.start();
    }
}
