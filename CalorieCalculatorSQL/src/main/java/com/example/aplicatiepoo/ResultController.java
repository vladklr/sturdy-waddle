package com.example.aplicatiepoo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;





public class ResultController extends CalorieCalculatorController implements Initializable {

    @FXML
    private Label resultLabel;

    @FXML
    private Label mildLabel;

    @FXML
    private Label weightLossLabel;

    @FXML
    private Label eWeightLossLabel;

    @FXML
    private Label mildGainLabel;

    @FXML
    private Label weightGainLabel;

    @FXML
    private Label fWeightGainLabel;

    @FXML
    private Button exitButton;

    public void exitButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resultLabel.setText(maintainWeight());
        mildLabel.setText(mildWeightLoss());
        weightLossLabel.setText(weightLoss());
        eWeightLossLabel.setText(eWeightLoss());
        mildGainLabel.setText(MildGain());
        weightGainLabel.setText(WeightGain());
        fWeightGainLabel.setText(FWeightGain());
    }



}
