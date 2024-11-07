module com.example.demomidtermtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demomidtermtest to javafx.fxml;
    exports com.example.demomidtermtest;
}