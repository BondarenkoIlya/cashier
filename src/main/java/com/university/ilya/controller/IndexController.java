package com.university.ilya.controller;

import com.university.ilya.manager.DialogManager;
import com.university.ilya.manager.SceneManager;
import com.university.ilya.model.Order;
import com.university.ilya.model.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ilya_Bondarenko
 */
public class IndexController extends Controller {
    @FXML
    public TableView tableOrder;
    @FXML
    public TableColumn<Product, String> columnPrice;
    @FXML
    public TableColumn<Product, String> columnNumber;
    @FXML
    public TableColumn<Product, String> columnName;
    @FXML
    public TextField totalPriceLabel;

    public ObservableList<Product> ordersProducts = FXCollections.observableArrayList();
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Stage manualAddProductDialogStage;
    private ManualProductsInsertController manualAddDialogController;
    private Parent fxmlProducts;
    private double totalPrice;
    private Scene indexScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        columnPrice.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPrice().toString()));
        columnNumber.setCellValueFactory(param -> new SimpleStringProperty("1"));

        tableOrder.setItems(ordersProducts);
        initLoader();
        initListeners();
    }

    private void initListeners() {
        ordersProducts.addListener((ListChangeListener<? super Product>) c -> updateTotalPrice());
    }

    private void updateTotalPrice() {
        totalPrice = 0;
        for (Product produst : ordersProducts) {
            totalPrice += produst.getPrice().getAmount().doubleValue();
        }
        totalPriceLabel.setText(String.valueOf(totalPrice));
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("/view/manual-product-insert-page.fxml"));
            fxmlProducts = fxmlLoader.load();
            manualAddDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showManualAddingPageAction(ActionEvent actionEvent) {
        manualAddDialogController.setOrder(ordersProducts);
        if (manualAddProductDialogStage == null) {
            manualAddProductDialogStage = new Stage();
            manualAddProductDialogStage.setMinHeight(400);
            manualAddProductDialogStage.setMinWidth(600);
            manualAddProductDialogStage.setResizable(false);
            manualAddProductDialogStage.setScene(new Scene(fxmlProducts));
            manualAddProductDialogStage.initModality(Modality.WINDOW_MODAL);
            manualAddProductDialogStage.initOwner(getStage());
        }
        manualAddProductDialogStage.showAndWait();
    }

    public void payAnOrderAction(ActionEvent actionEvent) {
        if (ordersProducts.isEmpty()){
            DialogManager.showErrorDialog("Внимание!", "Ваша корзина пуста, просканируйте продукты");
        }else {
            Order order = pickOrder();
            indexScene = getStage().getScene();
            PayOrderController controller = (PayOrderController) SceneManager.changeLocation(getStage(), "/view/pay-page.fxml");
            controller.setOrder(order);
            controller.setStage(getStage());
            controller.setIndexScene(indexScene);
        }
    }

    private Order pickOrder() {
        Order order = new Order();
        order.setProducts(ordersProducts);
        order.setTotalPrice(Money.of(CurrencyUnit.of(Product.currency), totalPrice));
        return order;
    }

    public void deleteItemAction(ActionEvent actionEvent) {
        Product selectedProduct = (Product) tableOrder.getSelectionModel().getSelectedItem();
        if (productIsSelected(selectedProduct)) {
            ordersProducts.remove(selectedProduct);
        }
    }

    private boolean productIsSelected(Product selectedProduct) {
        if (selectedProduct == null) {
            DialogManager.showErrorDialog("Внимание!", "Вы не выбрали продукт для удаления");
            return false;
        }
        return true;
    }

    public void deleteAllItemsAction(ActionEvent actionEvent) {
        ordersProducts.clear();
    }
}
