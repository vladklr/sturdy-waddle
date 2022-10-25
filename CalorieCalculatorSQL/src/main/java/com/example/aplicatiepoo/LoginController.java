package com.example.aplicatiepoo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private Button cancelButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    Button registerButton;

    Main m = new Main();



    public void loginButtonOnAction(ActionEvent e) {
        if (!usernameTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Enter username and password!");
        }
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM useraccounts WHERE Username = '" + usernameTextField.getText() + "' AND Password = '" + passwordPasswordField.getText() + "'";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginMessageLabel.setText("Welcome!");
                    m.changeScene("CalorieCalculator.fxml");
                } else {
                    loginMessageLabel.setText("Invalid Login. Please try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void switchToRegister(ActionEvent e) throws IOException {
        m.changeScene("Register.fxml");
    }


}