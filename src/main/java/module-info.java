module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens com.example.demo to javafx.graphics, javafx.fxml;
    //opens com.example.demo to javafx.fxml;//og
    exports com.example.demo.controller;
}