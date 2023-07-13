package com.example.fahrradverwaltung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EmployeeClientsController {

    @FXML
    private TableView<Client> clientsTable;
    @FXML
    private TableColumn<Client, String> usernameColumn;
    @FXML
    private TableColumn<Client, String> passwordColumn;
    @FXML
    private TableColumn<Client, String> firstNameColumn;
    @FXML
    private TableColumn<Client, String> lastNameColumn;
    @FXML
    private TableColumn<Client, String> emailColumn;
    @FXML
    private TableColumn<Client, String> balanceColumn;

    @FXML
    private void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        JSONArray clientsJson;
        try {
            clientsJson = new JSONArray(new String(Files.readAllBytes(Paths.get("users.json"))));
            for (int i = 0; i < clientsJson.length(); i++) {
                Client client = new Client(clientsJson.getJSONObject(i));
                clientsTable.getItems().add(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HelloApplication application;
    @FXML
    public void addBike(ActionEvent actionEvent) {
        try {
            application.showEmployeeAddBikeView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void viewClients() {

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
