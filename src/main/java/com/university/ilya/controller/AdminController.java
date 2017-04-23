package com.university.ilya.controller;

import com.university.ilya.manager.SceneManager;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController extends Controller {



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showInterConsignmentsAction(ActionEvent actionEvent) {
        SceneManager.changeLocation(getStage(), "/view/insert-consignment.fxml");
    }

    public void showOrderListPageAction(ActionEvent actionEvent) {
        SceneManager.changeLocation(getStage(), "/view/orders-page.fxml");
    }

    public void showConsignmentsListPageAction(ActionEvent actionEvent) {
        SceneManager.changeLocation(getStage(), "/view/consignments-list.fxml");
    }

    public void showProductsPageAction(ActionEvent actionEvent) {
        SceneManager.changeLocation(getStage(), "/view/products-page.fxml");
    }

    public void backToWorkAction(ActionEvent actionEvent) {
        SceneManager.changeLocation(getStage(), "/view/index.fxml");
    }
}
