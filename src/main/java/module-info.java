module com.example.walibixgr2eq6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.walibixgr2eq6 to javafx.fxml;
    exports com.example.walibixgr2eq6;
    exports com.example.walibixgr2eq6.controller;
    opens com.example.walibixgr2eq6.controller to javafx.fxml;
}