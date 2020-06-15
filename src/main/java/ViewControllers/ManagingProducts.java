package ViewControllers;

import Controller.Exceptions.ProductIsFinished;
import Controller.Exceptions.ThereISNotProductWithIdException;
import Controller.ManagerBoss;
import Main.Main;
import Model.Product;
import Model.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagingProducts implements Initializable {
    public TableView<Product> productsTable;
    public TableColumn<Product, Integer> PIDCol;
    public TableColumn<Product, String> nameCol;
    public TableColumn<Product, Double> priceCol;
    public Label actionInfo;
    public Label productInfo;

    private Product selectedProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateScreen();
    }

    ObservableList<Product> observableList = FXCollections.observableArrayList();

    private void updateScreen() {
        selectedProduct = null;
        productInfo.setText("");
        observableList.clear();
        PIDCol.setCellValueFactory(new PropertyValueFactory<>("IdForTable"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameForTable"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("PriceForTable"));
        observableList.addAll(Product.getAllProducts());
        productsTable.setItems(observableList);
    }

    public void clickProductsTable(MouseEvent mouseEvent) {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        productInfo.setText(selectedProduct.getName());
    }

    public void removeProductClick(MouseEvent mouseEvent) throws ThereISNotProductWithIdException {
        if (selectedProduct != null) {
            ManagerBoss.removeProductWithId(selectedProduct.getProductId());
            actionInfo.setTextFill(Color.GREEN);
            actionInfo.setText("Successful :)");
            updateScreen();
        }
        else {
            actionInfo.setText("Not Selected.");
            actionInfo.setTextFill(Color.RED);
        }
    }

    public void clickBack(MouseEvent mouseEvent) throws IOException {
        Main.tree.pop();
        Main.setRoot(Main.tree.peek(), Main.tree.peek());
    }
}
