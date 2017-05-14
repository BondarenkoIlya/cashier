package com.university.ilya.controller;

import com.university.ilya.manager.DialogManager;
import com.university.ilya.manager.SceneManager;
import com.university.ilya.model.Order;
import com.university.ilya.service.ConsignmentService;
import com.university.ilya.service.OrderService;
import com.university.ilya.service.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PayOrderController extends Controller {
    private Order order;
    private Parent indexRoot;
    private Scene indexScene;

    @FXML
    public Label totalPriceLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void backToIndexPage(ActionEvent actionEvent) {
        getStage().setScene(indexScene);
        getStage().show();
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

    public void payOrderAction(ActionEvent actionEvent) {
        OrderService orderService = new OrderService();
        ConsignmentService consignmentService = new ConsignmentService();
        try {
            orderService.saveOrder(order);
            consignmentService.subtractProducts(order);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        DialogManager.showInfoDialog("Операция завершена", "Возьмите свой чек! Всего хорошего");
        SceneManager.changeLocation(getStage(), "/view/index.fxml");
    }
}
