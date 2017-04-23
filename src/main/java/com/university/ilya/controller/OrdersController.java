package com.university.ilya.controller;

import com.university.ilya.manager.SceneManager;
import com.university.ilya.model.Order;
import com.university.ilya.service.OrderService;
import com.university.ilya.service.ServiceException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrdersController extends Controller {
    @FXML
    public TableView tableOrders;
    @FXML
    public TableColumn<Order, String> columnTime;
    @FXML
    public TableColumn<Order, String> columnTotalPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnTime.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTime().toString()));
        columnTotalPrice.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTotalPrice().toString()));
        initOrders();
    }

    private void initOrders() {
        OrderService service = new OrderService();
        List<Order> orders = null;
        try {
            orders = service.getAllOrders();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        tableOrders.setItems(FXCollections.observableList(orders));
    }

    public void backToAdminPageAction(ActionEvent actionEvent) {
        SceneManager.changeLocation(getStage(), "/view/admin-page.fxml");
    }
}
