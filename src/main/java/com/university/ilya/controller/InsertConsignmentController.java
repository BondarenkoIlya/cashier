package com.university.ilya.controller;

import com.university.ilya.manager.DialogManager;
import com.university.ilya.manager.SceneManager;
import com.university.ilya.model.Consignment;
import com.university.ilya.model.Product;
import com.university.ilya.service.ConsignmentService;
import com.university.ilya.service.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.net.URL;
import java.util.ResourceBundle;

public class InsertConsignmentController extends Controller {
    Consignment consignment;

    @FXML
    public TextField barcodeField;
    @FXML
    public TextField numberField;
    @FXML
    public TextField productField;
    @FXML
    public TextField priceField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void backToAdminPageAction(ActionEvent actionEvent) {
        SceneManager.changeLocation(getStage(), "/view/admin-page.fxml");
    }

    public void insertAction(ActionEvent actionEvent) {
        consignment = new Consignment();
        consignment.setNumberInPackage(Integer.valueOf(numberField.getText()));
        consignment.setActualNumber(Integer.valueOf(numberField.getText()));
        Product product = new Product(productField.getText(), Money.of(CurrencyUnit.of(Product.currency), Double.parseDouble(priceField.getText())), Integer.valueOf(barcodeField.getText()));
        consignment.setProduct(product);
        ConsignmentService consignmentService = new ConsignmentService();
        try {
            consignmentService.saveConsignment(consignment);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        DialogManager.showInfoDialog("Successful", "Закуп успешно введен");
        SceneManager.changeLocation(getStage(), "/view/admin-page.fxml");
    }
}

