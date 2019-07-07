package com.mine.six.gameclient;

import javafx.scene.control.Button;

import java.io.Serializable;

/**
 *  @author 肖又铭
 *  @author 李康
 */
public class GameStatus implements Serializable {
    String sessionId;

    @Override
    public String toString() {
        return "GameStatus{" +
                "sessionId='" + sessionId + '\'' +
                '}';
    }

    /**
     * 对应的是否按下
     */
    boolean[][] pressed;
    /**
     * 地雷图
     */
    boolean[][] mineMap;
    /**
     * 按钮上显示的数，如1，说明周围有1个雷
     */
    int [][] nums;
    /**
     * 用户标记
     */
    boolean[][] flags;

    int width;
    int height;
    /**
     * 总地雷数
     */
    int mineNum;
    /**
     * 标记数
     */
    int flagNum;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 已被打开的按钮数
     */
    int pressedNum;
    boolean isEnd;

    public GameStatus(boolean[][] pressed, boolean[][] mineMap, int[][] nums, boolean[][] flags, int width, int height, int mineNum, int flagNum, int pressedNum, boolean isEnd) {
        this.pressed = pressed;
        this.mineMap = mineMap;
        this.nums = nums;
        this.flags = flags;
        this.width = width;
        this.height = height;
        this.mineNum = mineNum;
        this.flagNum = flagNum;
        this.pressedNum = pressedNum;
        this.isEnd = isEnd;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
