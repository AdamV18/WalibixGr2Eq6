module com.example.walibixgr2eq6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.walibixgr2eq6 to javafx.fxml;
    exports com.example.walibixgr2eq6;
    exports com.example.walibixgr2eq6.Controller;
    opens com.example.walibixgr2eq6.Controller to javafx.fxml;
    opens com.example.walibixgr2eq6.Model to javafx.base;
    exports com.example.walibixgr2eq6.Model;

}