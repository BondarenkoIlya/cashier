package com.university.ilya.controller;

import com.university.ilya.manager.DialogManager;
import com.university.ilya.model.Product;
import com.university.ilya.service.ProductService;
import com.university.ilya.service.ServiceException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManualProductsInsertController extends Controller {

    @FXML
    public Button addProductToOrderBttn;

    @FXML
    public TextField barcodeTextField;

    @FXML
    private TableView tableProducts;

    @FXML
    private TableColumn<Product, String> columnName;

    @FXML
    private TableColumn<Product, String> columnPrice;

    @FXML
    private TableColumn<Product, String> columnBarcode;

    private ObservableList<Product> order;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        columnPrice.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPrice().toString()));
        columnBarcode.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getBarcode())));
        initProductTable();
    }

    private void initProductTable() {
        ProductService service = new ProductService();
        List<Product> products = null;
        try {
            products = service.getAllProducts();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        tableProducts.setItems(FXCollections.observableArrayList(products));
    }

    public void addProductToOrderAction(ActionEvent actionEvent) {
        Product selectedProduct = (Product) tableProducts.getSelectionModel().getSelectedItem();
        if (productIsSelected(selectedProduct)) {
            order.add(selectedProduct);
        }
        backToIndexPage(actionEvent);
    }

    private boolean productIsSelected(Product selectedProduct) {
        if (selectedProduct == null) {
            DialogManager.showErrorDialog("Внимание!", "Вы не выбрали продукт");
            return false;
        }
        return true;
    }

    public void setOrder(ObservableList<Product> order) {
        this.order = order;
    }

    public void findByBarcodeAction(ActionEvent actionEvent) {
        ProductService service = new ProductService();
        List<Product> products = null;
        try {
            if (barcodeTextField.getText().isEmpty()) {
                products = service.getAllProducts();
            } else {
                products = service.findByBarcode(Integer.parseInt(barcodeTextField.getText()));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        tableProducts.setItems(FXCollections.observableArrayList(products));
    }

    public void deleteLastCharacter(ActionEvent actionEvent) {
        String text = barcodeTextField.getText();
        barcodeTextField.setText(text.substring(0,text.length()-1));
    }

    public void inputNumberAction(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button button = (Button) source;
        barcodeTextField.appendText(button.getText());
    }

    public void backToIndexPage(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        barcodeTextField.clear();
        initProductTable();
    }
}
