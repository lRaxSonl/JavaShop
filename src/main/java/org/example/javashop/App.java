package org.example.javashop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.javashop.services.UserService;

import java.io.IOException;

public class App extends Application {
    private final static UserService userService = new UserService();
    @Override
    public void start(Stage stage) throws IOException {
        Singleton.getConnection();
        userService.createAdmin();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        stage.setTitle("JavaShop");
        stage.setScene(scene);
        stage.show();
    }
}
