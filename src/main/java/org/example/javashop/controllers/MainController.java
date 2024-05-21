package org.example.javashop.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.javashop.models.User;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button admin_panel_L;

    @FXML
    private Button products_L;

    @FXML
    private Button profile_L;

    @FXML
    private Label username1;

    @FXML
    void initialize() {
        User user = new LoginController().getUser();
        username1.setText(user.getUsername());

        profile_L.setOnAction(event -> {
            System.out.println(user.toString());
        });

    }
}
