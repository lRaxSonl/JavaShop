package org.example.javashop.contollers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SingupController {
    private static final UIHandler uiHandler = new UIHandler();

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

        /*
        login_button_link.setOnAction(event -> {
            //Отобразить окно с входом
            login_button_link.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/example/javashop/login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
         */
    }
}