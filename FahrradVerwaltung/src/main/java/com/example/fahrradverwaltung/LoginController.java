package com.example.fahrradverwaltung;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private HelloApplication application;

    private UserManager userManager = new UserManager();

    @FXML
    protected void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        JSONObject loggedUser = userManager.loginUser(username, password);

        if (loggedUser != null) {
            Utils.showAlert("Success", "Login was successful!", Alert.AlertType.INFORMATION);
            application.showUserMainView(loggedUser, userManager);
        } else if(userManager.loginEmployee(username, password)) {
            Utils.showAlert("Success", "Login was successful! [EMPLOYEE MODE]", Alert.AlertType.INFORMATION);
            application.showEmployeeMainView();  // If login is successful, switch to another view
        } else {
            Utils.showAlert("Error", "Login failed!", Alert.AlertType.ERROR);
        }
    }

    public void setApplication(HelloApplication application) {
        this.application = application;
    }

    @FXML
    private Button registerButton;

    @FXML
    protected void openRegister() throws IOException {
        application.showRegisterView();
    }

}
