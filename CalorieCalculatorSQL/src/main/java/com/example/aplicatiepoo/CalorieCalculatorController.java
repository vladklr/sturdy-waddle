package com.example.aplicatiepoo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class CalorieCalculatorController implements Initializable {

    @FXML
    private ChoiceBox<String> activityChoiceBox;

    @FXML
    private TextField weightTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private RadioButton femaleButton;

    @FXML
    private RadioButton maleButton;



    @FXML
    private Label infoLabel;

    public static String[] choices = {"Sedentary", "Light", "Moderate", "Active", "Very active", "Extra active"};
    public static double[] numbers = {1.2, 1.375, 1.55, 1.725, 1.90, 2.05};

    List<String> valid = Arrays.asList(choices);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activityChoiceBox.getItems().addAll(choices);
        activityChoiceBox.setOnAction(this::getActivity);
    }

    private static String activityChoice;


    public void getActivity(ActionEvent e) {
        activityChoice = activityChoiceBox.getValue();


    }


    public static boolean gen;


    Main m = new Main();


    public static int age;
    public static int weight;
    public static int height;


    public void calculateButtonOnAction() throws IOException {
        if (ageTextField.getText().isBlank() || weightTextField.getText().isBlank() || heightTextField.getText().isBlank()) {
            infoLabel.setText("Please complete the blank fields");
        } else if (!ageTextField.getText().isBlank() && !weightTextField.getText().isBlank() && !heightTextField.getText().isBlank()) {
            age = Integer.parseInt(ageTextField.getText());
            weight = Integer.parseInt(weightTextField.getText());
            height = Integer.parseInt(heightTextField.getText());
            if (age < 14 || age > 80) {
                System.out.println(ageTextField.getText());
                System.out.println(age);
                infoLabel.setText("enter a valid age");
            } else if (!maleButton.isSelected() && !femaleButton.isSelected())
                infoLabel.setText("select a gender");
            else if (height > 272)
                infoLabel.setText("enter a valid height");
            else if (weight > 600)
                infoLabel.setText("enter a valid weight");
            else if (!valid.contains(activityChoice))
                infoLabel.setText("Select an activity");
            else {
                gen = maleButton.isSelected();
                m.changeScene("Result.fxml");
            }
        }
    }

    public static double BMR;
    public String maintainWeight() {
        int i;

        for (i = 0; i < choices.length; i++) {
            if (choices[i].equals(activityChoice))
                break;
        }
        if (gen) {
            BMR = 66 + (13.75 * weight) + (5 * height) - (6.8 * age);

        }
        else
            BMR = 655 + (4.3 * weight) + (9.5 * weight) + (1.8 * height) - (4.7 * age);
        BMR = BMR * numbers[i];

        int newBMR = (int)BMR;
        return Integer.toString(newBMR);
    }

    public String mildWeightLoss () {
        double mildWeightLoss = BMR - BMR * 0.10;
        int newMildWeightLoss = (int)mildWeightLoss;
        return Integer.toString(newMildWeightLoss);
    }

    public String weightLoss () {
        double weightLoss = BMR - BMR * 0.20;
        int newWeightLoss = (int)weightLoss;
        return Integer.toString(newWeightLoss);
    }
    public String eWeightLoss () {
        double eWeightLoss = BMR - BMR * 0.30;
        int newEWeightLoss = (int)eWeightLoss;
        return Integer.toString(newEWeightLoss);
    }

    public String MildGain () {
        double mildGain = BMR + BMR * 0.10;
        int newMildGain = (int)mildGain;
        return Integer.toString(newMildGain);
    }

    public String WeightGain () {
        double weightGain = BMR + BMR * 0.20;
        int newWeightGain = (int)weightGain;
        return Integer.toString(newWeightGain);
    }

    public String FWeightGain () {
        double fWeightGain = BMR + BMR * 0.30;
        int newFWeightGain = (int)fWeightGain;
        return Integer.toString(newFWeightGain);
    }

}
