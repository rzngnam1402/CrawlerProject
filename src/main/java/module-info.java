module sample.ui {
    requires javafx.controls;
    requires javafx.fxml;


    opens sample.ui to javafx.fxml;
    exports sample.ui;
}