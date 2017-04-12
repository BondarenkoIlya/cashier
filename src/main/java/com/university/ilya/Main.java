package com.university.ilya;

import com.university.ilya.controller.IndexController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * @author Ilya_Bondarenko
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL resource = getClass().getResource("/view/index.fxml");

        fxmlLoader.setLocation(resource);

        Parent root = fxmlLoader.load();
        IndexController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        Scene indexScene = new Scene(root, 600, 400);
        controller.setIndexScene(indexScene);
        primaryStage.setTitle("Browser Desktop!");
        primaryStage.setScene(indexScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
