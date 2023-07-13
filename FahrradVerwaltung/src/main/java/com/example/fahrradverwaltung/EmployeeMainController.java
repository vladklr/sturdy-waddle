package com.example.fahrradverwaltung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeMainController {

    private HelloApplication application;
    @FXML
    private Button addBikeButton;

    @FXML
    private Button clientsButton;

    @FXML
    public void addBike(ActionEvent actionEvent) {
        try {
            application.showEmployeeAddBikeView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void viewClients(ActionEvent actionEvent) {
        try {
            application.showEmployeeClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setApplication(HelloApplication application) {
        this.application = application;
    }

    public void logout(ActionEvent actionEvent) {
        try {
            application.showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
