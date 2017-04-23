package com.university.ilya.controller;

import com.university.ilya.manager.SceneManager;
import com.university.ilya.model.Consignment;
import com.university.ilya.service.ConsignmentService;
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

public class ConsignmentsController extends Controller {

    @FXML
    public TableView tableConsignments;
    @FXML
    public TableColumn<Consignment, String> columnProductName;
    @FXML
    public TableColumn<Consignment, String> columnNumberInPackage;
    @FXML
    public TableColumn<Consignment, String> columnActualNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnActualNumber.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getActualNumber())));
        columnNumberInPackage.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getNumberInPackage())));
        columnProductName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProduct().getName()));

        initConsignmentList();
    }

    private void initConsignmentList() {
        ConsignmentService consignmentService = new ConsignmentService();
        List<Consignment> consignments = null;
        try {
            consignments = consignmentService.getAllConsignments();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        tableConsignments.setItems(FXCollections.observableList(consignments));
    }

    public void backToAdminPageAction(ActionEvent actionEvent) {
        SceneManager.changeLocation(getStage(), "/view/admin-page.fxml");
    }
}
