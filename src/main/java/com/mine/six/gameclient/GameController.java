package com.mine.six.gameclient;

import com.mine.six.utils.TimeCount;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


import java.util.Random;

/**
 * 游戏界面的控制
 * @author xiaoyouming
 */
public class GameController{

    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private GridPane pane;
    @FXML
    private Label label;
    @FXML
    private Label time;
    @FXML
    private HBox hBox;
    /**
     * 按钮
     */
    Button[][] btns;
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
    /**
     * 已被打开的按钮数
     */
    int pressedNum;
    boolean isEnd;

    public void initialize() {
        choiceBox.setOnAction(e -> {
            String value = choiceBox.getValue();
            String[] ss = value.split("\\*");
            String[] split = ss[1].split("\\|");
            height = Integer.parseInt(ss[0]);
            width = Integer.parseInt(split[0]);
            mineNum = Integer.parseInt(split[1]);
            play();
        });
        choiceBox.getSelectionModel().select(1);
    }

    public void play(){
        mineMap = new boolean[height][width];
        pressed = new boolean[height][width];
        btns = new Button[height][width];
        nums = new int[height][width];
        flags = new boolean[height][width];
        pane.getChildren().clear();
        flagNum = 0;
        pressedNum = 0;
        label.setText(String.valueOf(mineNum));
        int rest = mineNum;
        //随机放置地雷
        Random r = new Random(System.nanoTime());
        while (rest > 0) {
            int n = r.nextInt(height * width);
            //避免重复放置
            if(!mineMap[n / width][n % width]){
                mineMap[n / width][n % width] = true;
                rest--;
            }
        }
        //初始化地图
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Button btn = getBtn(j, i);
                btns[i][j] = btn;
                nums[i][j] = countMinesAround(j, i);
                pane.add(btn, j, i);
            }
        }
        TimeCount.Start();
    }
    public GameStatus getGameState(){
        return new GameStatus(btns,pressed,mineMap,nums,flags,width,height,mineNum,flagNum,pressedNum,isEnd);
    }

    public int countMinesAround(int x, int y){
        int res = 0;
        for (int i = Math.max(0, y - 1); i <= Math.min(y + 1, height - 1); i++) {
            for (int j = Math.max(0, x - 1); j <= Math.min(x + 1, width - 1); j++) {
                boolean flag=(y != i || x != j);
                if(flag && mineMap[i][j]){
                    res++;
                }
            }
        }
        return res;
    }

    public Button getBtn(int x, int y){
        Button button = new Button();
        button.setPrefSize(30, 30);
        button.setMinSize(30, 30);
        button.setStyle("-fx-background-color: darkcyan;");
        button.setOnMouseClicked(e -> {
            //左键
            if(e.getClickCount() == 2 && pressed[y][x]){
                int count = 0;
                for (int i = Math.max(0, y - 1); i <= Math.min(y + 1, height - 1); i++) {
                    for (int j = Math.max(0, x - 1); j <= Math.min(x + 1, width - 1); j++) {
                        if(flags[i][j]) {
                            count++;
                        }
                    }
                }
                if (count != nums[y][x]){
                    return;
                }
                for (int i = Math.max(0, y - 1); i <= Math.min(y + 1, height - 1); i++) {
                    for (int j = Math.max(0, x - 1); j <= Math.min(x + 1, width - 1); j++) {
                        if (isEnd){
                            isEnd = false;
                            return;
                        }
                        if(!flags[i][j]){
                            open(j, i);
                        }
                    }
                }
                return;
            }
            //右键
            if (MouseButton.SECONDARY == e.getButton()) {
                if(!flags[y][x]) {
                    flagNum++;
                    flags[y][x] = true;
                    Shape shape = new Circle(4, Color.AQUA);
                    button.setGraphic(shape);
                }else {
                    flagNum--;
                    flags[y][x] = false;
                    button.setGraphic(null);
                }
            } else {
                if(flags[y][x]) {
                    flagNum--;
                    button.setGraphic(null);
                }
                open(x, y);
            }
            updateRemain();
        });
        return button;
    }

    public void updateRemain(){
        label.setText(String.valueOf(mineNum - flagNum));
    }

    public void open(int x, int y){
        if (pressed[y][x]){
            return;
        }
        pressed[y][x] = true;
        Button button = btns[y][x];
        if (mineMap[y][x]) {
            TimeCount.End();
            isEnd = true;
            button.setText("*");
            button.setStyle("-fx-background-color: crimson");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("你输了！用时"+TimeCount.getSecondTime()+"秒\n再来一次 ...");
            alert.setTitle("提示");
            alert.showAndWait();
            play();
        }else {
            button.setStyle("-fx-background-color: dimgrey");
            pressedNum++;
            if (pressedNum == height * width - mineNum){
                TimeCount.End();
                isEnd = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("你赢了！用时"+TimeCount.getSecondTime()+"秒");
                alert.setTitle("提示");
                alert.showAndWait();
                play();
                return;
            }
            int count = nums[y][x];
            if (count > 0){
                button.setText(String.valueOf(count));
            }
            else {
                for (int i = Math.max(0, y - 1); i <= Math.min(y + 1, height - 1); i++) {
                    for (int j = Math.max(0, x - 1); j <= Math.min(x + 1, width - 1); j++) {
                        if(!flags[i][j]){
                            open(j, i);
                        }
                    }
                }
            }
        }
    }

    public void fullScreen(ActionEvent e){
        Stage window = (Stage) pane.getScene().getWindow();
        window.setFullScreen(!window.isFullScreen());
    }
}
