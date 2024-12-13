module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens com.example.demo to javafx.graphics, javafx.fxml;
    //opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.actors to javafx.fxml, javafx.graphics;
    opens com.example.demo.menus to javafx.fxml, javafx.graphics;
    opens com.example.demo.displays to javafx.fxml, javafx.graphics;
    opens com.example.demo.levels to javafx.fxml, javafx.graphics;
}