module com.example.ui {
    requires javafx.controls;
    requires javafx.fxml;

//    requires org.controlsfx.controls;
//    requires org.kordamp.bootstrapfx.core;
    requires org.jsoup;
    requires com.google.gson;
    requires javafx.base;

    opens com.example.ui to javafx.fxml;
    opens model_crawler.nhanvat to com.google.gson;
    opens model_crawler.lehoi to com.google.gson;
    opens model_crawler.thoiky to com.google.gson;
    opens model_crawler.diadiem to com.google.gson;
    opens model_crawler.sukien to com.google.gson;

    exports com.example.ui;
}