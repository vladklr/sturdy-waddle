package com.example.aplicatiepoo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;

public class RegisterController {


    @FXML
    private Label registrationMessageLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;
    Main m = new Main();

    public void returnButtonOnAction(ActionEvent event) throws IOException {
        m.changeScene("Login.fxml");
    }

    public void registerButtonOnAction(ActionEvent e) {
        if (usernameTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false) {
            registerUser();
            registrationMessageLabel.setTextFill(Color.web("#00FF00"));
            registrationMessageLabel.setText("Registration successfully!");
        } else {
            registrationMessageLabel.setTextFill(Color.web("#FF0000"));
            registrationMessageLabel.setText("Enter username and password!");
        }
    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        String insertFields = "INSERT INTO useraccounts(Username, Password) VALUES('";
        String insertValues = username + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("Registration successfully");
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
