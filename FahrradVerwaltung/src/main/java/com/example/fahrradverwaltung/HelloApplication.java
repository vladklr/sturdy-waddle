package com.example.fahrradverwaltung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class HelloApplication extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        showLoginView();
        stage.show();
    }

    public void showLoginView() throws IOException {
        // FXMLLoader for login
        FXMLLoader loginLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene loginScene = new Scene(loginLoader.load());

        LoginController loginController = loginLoader.getController();
        loginController.setApplication(this);

        stage.setTitle("Login");
        stage.setScene(loginScene);
    }

    public void showRegisterView() throws IOException {
        // FXMLLoader for register
        FXMLLoader registerLoader = new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
        Scene registerScene = new Scene(registerLoader.load());

        RegisterController registerController = registerLoader.getController();
        registerController.setApplication(this);

        stage.setTitle("Register");
        stage.setScene(registerScene);
    }

    public void showEmployeeMainView() throws IOException {
        // FXMLLoader for register
        FXMLLoader employeeMainLoader = new FXMLLoader(HelloApplication.class.getResource("employeeMain-view.fxml"));
        Scene employeeMainScene = new Scene(employeeMainLoader.load());

        EmployeeMainController employeeMainController = employeeMainLoader.getController();
        employeeMainController.setApplication(this);

        stage.setTitle("Employee-Main");
        stage.setScene(employeeMainScene);
    }

    public void showEmployeeAddBikeView() throws IOException {
        FXMLLoader employeeAddBikeLoader = new FXMLLoader(HelloApplication.class.getResource("employeeAddBike-view.fxml"));
        Scene employeeAddBikeScene = new Scene(employeeAddBikeLoader.load());

        EmployeeAddBikeController employeeAddBikeController = employeeAddBikeLoader.getController();
        employeeAddBikeController.setApplication(this);

        stage.setTitle("Employee-AddBike");
        stage.setScene(employeeAddBikeScene);
    }

    public void showEmployeeClients() throws IOException {
        FXMLLoader employeeClientsLoader = new FXMLLoader(HelloApplication.class.getResource("employeeClients-view.fxml"));
        Scene employeeClientsScene = new Scene(employeeClientsLoader.load());

        EmployeeClientsController employeeClientsController = employeeClientsLoader.getController();
        employeeClientsController.setApplication(this);

        stage.setTitle("Employee-Clients");
        stage.setScene(employeeClientsScene);
    }

    public void showUserMainView(JSONObject loggedUser, UserManager userManager) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("userMain-view.fxml"));
        Parent userMainView = loader.load();

        UserMainController controller = loader.getController();
        controller.initData(loggedUser);
        controller.setUserManager(userManager);
        controller.setStage(stage);
        controller.setApplication(this);  // Pass the application reference

        stage.setScene(new Scene(userMainView));
        stage.setTitle("User-Main");
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}
