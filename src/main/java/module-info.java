module com.example.vlaknalight {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.vlaknalight to javafx.fxml;
    exports com.example.vlaknalight;
}