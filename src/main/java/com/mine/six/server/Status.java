package com.mine.six.server;

import com.mine.six.gameclient.GameStatus;

import java.io.Serializable;

/**
 * 游戏状态，游戏界面通过解析Status来绘制
 * @author xiaoyouming
 */
public class Status implements Serializable {


    //保存会话数据
    private int SessionId;
    private GameStatus gameStatus;
    Status(int sessionId,GameStatus status){
        this.SessionId=sessionId;
        this.gameStatus=status;
    }
    public int getSessionId() {
        return SessionId;
    }

    public void setSessionId(int sessionId) {
        SessionId = sessionId;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGamestatus(GameStatus status) {
        this.gameStatus=status;
    }

}
