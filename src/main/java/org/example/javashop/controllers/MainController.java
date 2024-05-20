package org.example.javashop.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.javashop.models.User;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> main_listview;

    @FXML
    private Label username1;

    @FXML
    void initialize() {
        main_listview.getItems().addAll("Профиль", "Товары", "Админ панель");
        User user = new LoginController().getUser();

        username1.setText(user.getUsername());
    }
}
