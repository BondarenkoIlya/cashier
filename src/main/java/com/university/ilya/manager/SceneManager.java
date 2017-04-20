package com.university.ilya.manager;

import com.university.ilya.controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {

    public static Controller changeLocation(Stage stage, String page) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL resource = SceneManager.class.getResource(page);
        fxmlLoader.setLocation(resource);
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controller controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("Cashier");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
        return controller;
    }
}
