package org.example.javashop.contollers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UIHandler {
    public void changeWindow(Button button, String src) {
        button.setOnAction(event -> {
            //Отобразить окно с входом

            //Скрыть текущее окно
            button.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(src));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("JavaShop");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
}
