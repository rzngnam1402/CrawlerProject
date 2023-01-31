module com.example.ui {
    requires javafx.controls;
    requires javafx.fxml;

//    requires org.controlsfx.controls;
//    requires org.kordamp.bootstrapfx.core;
    requires org.jsoup;
    requires com.google.gson;
    requires javafx.base;

    opens com.example.ui to javafx.fxml;
    exports com.example.ui;
}