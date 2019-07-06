package com.mine.six.gameclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *  @author 肖又铭
 *  @author 李康
 */
public class Client implements Runnable{
    private String msg;
    private GameStatus gameStatus;
    public void start(){
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ClientInitializer());
        try {
            Channel channel=bootstrap.connect("localhost",10800).sync().channel();
            while (true){
                if (StringUtils.isNotEmpty(msg)) {
                    //channel.writeAndFlush(msg);
                }
                if (gameStatus!=null) {
                //将gameState进行序列化
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(gameStatus);
                objectOutputStream.flush();
                channel.writeAndFlush(byteArrayOutputStream.toByteArray());
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus=gameStatus;
    }
    public void setMsg(String msg){
        this.msg=msg;
    }

    @Override
    public void run() {
        start();
    }
}
