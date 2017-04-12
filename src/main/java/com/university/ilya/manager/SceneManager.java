package com.university.ilya.manager;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {

    public static Initializable changeLocation(Stage stage, String page) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL resource = SceneManager.class.getResource(page);
        fxmlLoader.setLocation(resource);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Cashier");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        return fxmlLoader.getController();
    }
}
