module com.example.aplicatiepoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.aplicatiepoo to javafx.fxml;
    exports com.example.aplicatiepoo;
}