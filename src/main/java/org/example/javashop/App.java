package org.example.javashop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Singleton.getConnection();
        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        stage.setTitle("JavaShop");
        stage.setScene(scene);
        stage.show();
    }
}
