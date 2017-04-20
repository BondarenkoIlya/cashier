package com.university.ilya;

import com.university.ilya.manager.SceneManager;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * @author Ilya_Bondarenko
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.changeLocation(primaryStage, "/view/index.fxml");
        primaryStage.addEventHandler( KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                SceneManager.changeLocation(primaryStage, "/view/admin-page.fxml");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
