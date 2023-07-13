package com.example.fahrradverwaltung;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class UserMainController {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label balanceLabel;

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
    private TableColumn<Bike, String> rentedUntilColumn;

    private Client loggedUser;

    private UserManager userManager;

    private Stage stage;

    private HelloApplication application;

    public void setApplication(HelloApplication application) {
        this.application = application;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        batteryColumn.setCellValueFactory(new PropertyValueFactory<>("battery"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
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

    public void initData(JSONObject loggedUser) {
        // Adjust according to JSON object keys
        usernameLabel.setText("User: " + loggedUser.getString("username"));
        balanceLabel.setText("Balance: " + loggedUser.getString("balance") + "$");
        this.loggedUser = new Client(loggedUser);
    }

    @FXML
    private Button addBalanceButton;

    @FXML
    private void addBalance() {
        // Open a new dialog to ask the user how much they want to add
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Balance");
        dialog.setHeaderText("Enter the amount you want to add to your balance:");
        Optional<String> result = dialog.showAndWait();

        // If user has entered a value
        if (result.isPresent()) {
            String addedAmount = result.get();

            // Check if the entered value is a valid number
            try {
                double addedAmountDouble = Double.parseDouble(addedAmount);

                // Update the user's balance
                double currentBalance = Double.parseDouble(loggedUser.getBalance());
                loggedUser.setBalance(String.valueOf(currentBalance + addedAmountDouble));

                // Update the balance label
                balanceLabel.setText("Balance: " + loggedUser.getBalance() + "$");

                // Save the updated user data back to the file
                userManager.saveUserData(loggedUser);

            } catch (NumberFormatException e) {
                // Show an error dialog if the entered value is not a valid number
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Invalid amount entered");
                alert.setContentText("Please enter a valid number.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void openLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login-view.fxml"));
        Parent loginView = loader.load();

        LoginController controller = loader.getController();
        controller.setApplication(application);

        Scene loginScene = new Scene(loginView);
        stage.setScene(loginScene);
    }

    public void rentBike(ActionEvent actionEvent) {
        // Get the selected bike from the table
        Bike selectedBike = bikeTable.getSelectionModel().getSelectedItem();

        // Check if a bike is selected
        if (selectedBike == null) {
            Utils.showAlert("Error", "No bike selected!", Alert.AlertType.ERROR);
            return;
        }

        // Check if the bike is already rented
        if (!selectedBike.getRented_by().isEmpty() || !selectedBike.getRented_until().isEmpty()) {
            Utils.showAlert("Error", "The selected bike is already rented!", Alert.AlertType.ERROR);
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rent Bike");
        dialog.setHeaderText("Enter the number of days you want to rent the bike:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String rentalDays = result.get();

            // Check if the entered value is a valid number
            try {
                int rentalDaysInt = Integer.parseInt(rentalDays);

                // Check if the user has enough balance to rent the bike
                double currentBalance = Double.parseDouble(loggedUser.getBalance());
                double bikePrice = Double.parseDouble(selectedBike.getPrice().replace("$", ""));

                if (currentBalance < bikePrice * rentalDaysInt) {
                    Utils.showAlert("Error", "You do not have enough balance to rent this bike!", Alert.AlertType.ERROR);
                    return;
                }

                // Rent the bike
                selectedBike.setRented_by(loggedUser.getUsername());

                // Calculate the rental until date
                LocalDate currentDate = LocalDate.now();
                LocalDate rentedUntilDate = currentDate.plusDays(rentalDaysInt);
                selectedBike.setRented_until(rentedUntilDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

                // Update the user's balance
                loggedUser.setBalance(String.valueOf(currentBalance - bikePrice * rentalDaysInt));
                balanceLabel.setText("Balance: " + loggedUser.getBalance() + "$");

                // Save the updated user and bike data back to the files
                userManager.saveUserData(loggedUser);

                saveBikeData(selectedBike);

                bikeTable.refresh();

            } catch (NumberFormatException | IOException e) {
                // Show an error dialog if the entered value is not a valid number
                Utils.showAlert("Error", "Invalid number of days entered", Alert.AlertType.ERROR);
            }
        }
    }

    private void saveBikeData(Bike selectedBike) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/main/resources/bikes.json")));
        JSONArray bikesJson = new JSONArray(content);

        for (int i = 0; i < bikesJson.length(); i++) {
            JSONObject bikeJson = bikesJson.getJSONObject(i);
            if (bikeJson.getString("id").equals(selectedBike.getId())) {
                // Replace with the updated bike data
                bikeJson.put("rented_by", selectedBike.getRented_by());
                bikeJson.put("rented_until", selectedBike.getRented_until());
                break;  // Break the loop once the bike is found and updated
            }
        }

        Files.write(Paths.get("src/main/resources/bikes.json"), bikesJson.toString().getBytes());
    }
}
