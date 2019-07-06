package com.mine.six.gameclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 程序开始
 * @author xiaoyouming
 */
public class Main extends Application {

    private Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage=primaryStage;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        primaryStage.setTitle("扫雷");
        primaryStage.getIcons().add(new Image("/imgs/timg.jpg"));
        LoginController lc=loader.getController();
        lc.setloginController(this);
        primaryStage.setScene(new Scene(root, 600, 360));
        primaryStage.show();
    }

    public void gotoGame() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/Game.fxml"));
        Parent root=loader.load();
        GameController gc=loader.getController();
        gc.initialize();
        Scene sc=new Scene(root,600,360);
        stage.setTitle("扫雷");
        stage.setScene(sc);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
