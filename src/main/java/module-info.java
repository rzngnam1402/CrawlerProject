module com.example.ui {
    requires javafx.controls;
    requires javafx.fxml;

//    requires org.controlsfx.controls;
//    requires org.kordamp.bootstrapfx.core;

    requires org.jsoup;
    requires com.google.gson;

    opens com.example.ui to javafx.fxml;

    opens model_crawler.nhanvat to com.google.gson;
    exports com.example.ui;
}