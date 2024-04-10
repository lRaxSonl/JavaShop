module org.example.javashop {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires static lombok;
    requires shiro.core;


    opens org.example.javashop to javafx.fxml;
    exports org.example.javashop;
    exports org.example.javashop.contollers;
    opens org.example.javashop.contollers to javafx.fxml;
}