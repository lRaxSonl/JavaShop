package org.example.javashop.contollers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private static final UIHandler uiHandler = new UIHandler();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button login_button;

    @FXML
    private PasswordField login_passwordField;

    @FXML
    private TextField login_textField;

    @FXML
    private Button reg_button_link;

    @FXML
    void initialize() {
        uiHandler.changeWindow(reg_button_link, "/org/example/javashop/singup.fxml");

        /*
        reg_button_link.setOnAction(event -> {
            //Отобразить окно с регистрацией
            reg_button_link.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/org/example/javashop/singup.fxml"));

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

        login_button.setOnAction(event -> {
            System.out.println("Button has been pressed");
        });

         */
    }
}
