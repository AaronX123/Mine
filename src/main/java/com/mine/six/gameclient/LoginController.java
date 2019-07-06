package com.mine.six.gameclient;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;

/**
 * 登录界面的控制
 *  @author 肖又铭
 *  @author 李康
 */
public class LoginController {

    private Main app;
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton register;

    public void setLoginController(Main app){
        this.app=app;
    }
    @FXML
    void Login(ActionEvent event) {
        String userName=username.getText();
        String passWord=password.getText();
        if ("123".equals(userName)&&"123".equals(passWord)){
            System.out.println("1");
            try {
                app.gotoGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"用户名或密码错误", ButtonType.OK).show();
        }
    }

    @FXML
    void Register(ActionEvent event) {

    }

}
