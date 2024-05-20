package org.example.javashop.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.javashop.models.User;
import org.example.javashop.services.UserService;

public class LoginController {
    private static final UIHandler uiHandler = new UIHandler();
    private static final UserService userService = new UserService();
    static User user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button login_button;

    @FXML
    private PasswordField login_passwordFieldForPassword;

    @FXML
    private TextField login_textFieldForUsername;

    @FXML
    private Button reg_button_link;

    @FXML
    void initialize() {
        login_button.setOnAction(event -> {
            String username = login_textFieldForUsername.getText().trim();
            String password = login_passwordFieldForPassword.getText().trim();

            user = userService.userLogin(username, password);

            try {
                if(!user.equals(null)) {
                    uiHandler.changeWindow(login_button, "/org/example/javashop/main.fxml");
                }
            }catch (NullPointerException e) {
                System.out.println("Fields is null");
            }
        });


        uiHandler.changeWindow(reg_button_link, "/org/example/javashop/singup.fxml");
    }

    public static User getUser() {
        return user;
    }
}
