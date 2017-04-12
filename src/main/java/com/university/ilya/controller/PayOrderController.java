package com.university.ilya.controller;

import com.university.ilya.model.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PayOrderController implements Initializable {
    @FXML
    public Label totalPriceLabel;
    private Stage stage;
    private Order order;
    private Parent indexRoot;
    private Scene indexScene;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void backToIndexPage(ActionEvent actionEvent) {
        stage.setScene(indexScene);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setOrder(Order order) {
        this.order = order;
        totalPriceLabel.setText(order.getTotalPrice().toString());
    }

    public void setIndexRoot(Parent indexRoot) {
        this.indexRoot = indexRoot;
    }

    public void setIndexScene(Scene indexScene) {
        this.indexScene = indexScene;
    }
}
