module com.example.fahrradverwaltung {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens com.example.fahrradverwaltung to javafx.fxml;
    exports com.example.fahrradverwaltung;
}