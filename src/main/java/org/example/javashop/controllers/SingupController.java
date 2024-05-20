package org.example.javashop.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.javashop.services.UserService;

public class SingupController {
    private static final UIHandler uiHandler = new UIHandler();
    private static final UserService userService = new UserService();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button login_button_link;

    @FXML
    private Button reg_button;

    @FXML
    private PasswordField reg_passwordField;

    @FXML
    private TextField reg_textField;

    @FXML
    void initialize() {
        uiHandler.changeWindow(login_button_link, "/org/example/javashop/login.fxml");

        reg_button.setOnAction(event -> {
            String username = reg_textField.getText().trim();
            String password = reg_passwordField.getText().trim();

            userService.addNewUser(username, password);
        });
    }
}
