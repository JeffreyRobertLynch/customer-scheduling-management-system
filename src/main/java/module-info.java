module jrl.qam2final {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens jrl.qam2final to javafx.fxml;
    exports jrl.qam2final.Controller;
    opens jrl.qam2final.Controller to javafx.fxml;
    exports jrl.qam2final.Main;
    opens jrl.qam2final.Main to javafx.fxml;
    exports jrl.qam2final.Model;
    opens jrl.qam2final.Model to javafx.fxml;
}