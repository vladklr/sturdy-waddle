package com.example.fahrradverwaltung;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private HelloApplication application;
    private UserManager userManager = new UserManager();
    private static final String VALID_USERNAME_PASSWORD_REGEX = ".{4,}";

    public void setApplication(HelloApplication application) {
        this.application = application;
    }

    @FXML
    protected void register() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String balance = "0";

        if(userManager.userExists(username)) {
            Utils.showAlert("Error", "Username is already taken!", Alert.AlertType.ERROR);
        } else if(!Pattern.matches(VALID_USERNAME_PASSWORD_REGEX, username)) {
            Utils.showAlert("Error", "Username should have at least 4 characters!", Alert.AlertType.ERROR);
        } else if(!Pattern.matches(VALID_USERNAME_PASSWORD_REGEX, password)) {
            Utils.showAlert("Error", "Password should have at least 4 characters!", Alert.AlertType.ERROR);
        } else if(userManager.registerUser(username, password, firstName, lastName, email, balance)) {
            Utils.showAlert("Success", "Registration was successful!", Alert.AlertType.INFORMATION);
            // Registration successful, switch to login view
            application.showLoginView();
        } else {
            // Registration failed, user exists
            Utils.showAlert("Error", "Registration failed!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void openLogin() throws IOException {
        application.showLoginView();
    }
}
