module ua.edu.nuos.lab456graphics {
    requires javafx.controls;
    requires javafx.fxml;


    opens ua.edu.nuos.lab456graphics to javafx.fxml;
    exports ua.edu.nuos.lab456graphics;
}