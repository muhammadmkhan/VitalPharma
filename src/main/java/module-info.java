module com.example.vitalpharma {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.vitalpharma to javafx.fxml;
    exports com.example.vitalpharma;
}