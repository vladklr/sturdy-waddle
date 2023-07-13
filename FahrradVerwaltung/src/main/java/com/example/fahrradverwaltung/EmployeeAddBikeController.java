package com.example.fahrradverwaltung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EmployeeAddBikeController {
    @FXML
    private TableView<Bike> bikeTable;
    @FXML
    private TableColumn<Bike, Integer> idColumn;
    @FXML
    private TableColumn<Bike, String> nameColumn;
    @FXML
    private TableColumn<Bike, String> batteryColumn;
    @FXML
    private TableColumn<Bike, String> priceColumn;
    @FXML
    private TableColumn<Bike, String> rentedByColumn;
    @FXML
    private TableColumn<Bike, String> rentedUntilColumn;

    @FXML
    private Button addBikeButton;

    @FXML
    private Button clientsButton;

    @FXML
    private TextField bikeNameField;

    @FXML
    private TextField bikeIDField;

    @FXML
    private TextField batteryField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField rentedByField;

    @FXML
    private TextField rentedUntilField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;
    private HelloApplication application;


    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        batteryColumn.setCellValueFactory(new PropertyValueFactory<>("battery"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        rentedByColumn.setCellValueFactory(new PropertyValueFactory<>("rented_by"));
        rentedUntilColumn.setCellValueFactory(new PropertyValueFactory<>("rented_until"));

        JSONArray bikesJson;
        try {
            bikesJson = new JSONArray(new String(Files.readAllBytes(Paths.get("src/main/resources/bikes.json"))));
            for (int i = 0; i < bikesJson.length(); i++) {
                Bike bike = new Bike(bikesJson.getJSONObject(i));
                bikeTable.getItems().add(bike);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addBike(ActionEvent event) {
    }

    @FXML
    public void viewClients(ActionEvent actionEvent) {
        try {
            application.showEmployeeClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewBike(ActionEvent actionEvent) {
        String bikeName = bikeNameField.getText();
        String bikeID = bikeIDField.getText();
        String batteryStatus = batteryField.getText();
        String price = priceField.getText();
        String rentedBy = rentedByField.getText();
        String rentedUntil = rentedUntilField.getText();

        try {
            Integer.parseInt(bikeID);
            Integer.parseInt(price);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The Bike ID and Price fields should be integers!");
            alert.showAndWait();
            return;
        }

        // Check if rentedUntil is a date / if it's not empty
        if (!rentedUntil.isEmpty()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate.parse(rentedUntil, dtf);
            } catch (DateTimeParseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The Rented Until field should be a date in the format 'yyyy-mm-dd'!");
                alert.showAndWait();
                return;
            }
        }

        if ((rentedBy.isEmpty() && !rentedUntil.isEmpty()) || (!rentedBy.isEmpty() && rentedUntil.isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Both Rented by and Rented Until fields should be either both empty or both filled!");
            alert.showAndWait();
            return;
        }

        JSONObject newBike = new JSONObject();
        newBike.put("id", bikeID);
        newBike.put("name", bikeName);
        newBike.put("battery", batteryStatus);
        newBike.put("price", price + "$");
        newBike.put("rented_by", rentedBy);
        newBike.put("rented_until", rentedUntil);

        // Read the existing array from the file
        JSONArray bikesArray;
        try {
            bikesArray = new JSONArray(new String(Files.readAllBytes(Paths.get("src/main/resources/bikes.json"))));

            // Check if the ID already exists
            for (int i = 0; i < bikesArray.length(); i++) {
                JSONObject bikeObj = bikesArray.getJSONObject(i);
                if (bikeObj.getString("id").equals(bikeID)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The ID is already taken!");
                    alert.showAndWait();
                    return;
                }
            }

            // If not, add the new bike to the array
            bikesArray.put(newBike);

            // Write the whole array back to the file
            Files.write(Paths.get("src/main/resources/bikes.json"), bikesArray.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);

            Bike bike = new Bike(newBike);
            bikeTable.getItems().add(bike);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear the input fields
        bikeNameField.clear();
        bikeIDField.clear();
        batteryField.clear();
        priceField.clear();
        rentedByField.clear();
        rentedUntilField.clear();
    }

    public void deleteBike(ActionEvent event) {
        // Get selected bike
        Bike selectedBike = bikeTable.getSelectionModel().getSelectedItem();

        // Remove from table
        bikeTable.getItems().remove(selectedBike);

        // Remove from JSON file
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/bikes.json")));
            JSONArray bikesArray = new JSONArray(content);
            for (int i = 0; i < bikesArray.length(); i++) {
                JSONObject bikeObj = bikesArray.getJSONObject(i);
                if (String.valueOf(bikeObj.getInt("id")).equals(selectedBike.getId())) {
                    bikesArray.remove(i);
                    break;
                }
            }
            Files.write(Paths.get("src/main/resources/bikes.json"), bikesArray.toString().getBytes());
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
