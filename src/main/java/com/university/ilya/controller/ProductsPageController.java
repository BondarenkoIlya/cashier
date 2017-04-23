package com.university.ilya.controller;

import com.university.ilya.manager.SceneManager;
import com.university.ilya.model.Product;
import com.university.ilya.service.ProductService;
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

public class ProductsPageController extends Controller {
    @FXML
    public TableView tableProducts;
    @FXML
    public TableColumn<Product, String> columnName;
    @FXML
    public TableColumn<Product, String> columnBarcode;
    @FXML
    public TableColumn<Product, String> columnPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        columnBarcode.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getBarcode())));
        columnPrice.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPrice().toString()));
        initProducts();
    }

    private void initProducts() {
        ProductService service = new ProductService();
        List<Product> allProducts = null;
        try {
            allProducts = service.getAllProducts();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        tableProducts.setItems(FXCollections.observableList(allProducts));
    }

    public void backToAdminPageAction(ActionEvent actionEvent) {
        SceneManager.changeLocation(getStage(), "/view/admin-page.fxml");
    }
}
