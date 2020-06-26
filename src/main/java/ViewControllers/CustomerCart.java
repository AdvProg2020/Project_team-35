package ViewControllers;

import Controller.Exceptions.InventoryException;
import Controller.ProductBoss;
import Main.Main;
import Model.Account;
import Model.Category;
import Model.Customer;
import Model.Product;
import MusicPlayer.MusicPlayer;
import com.sun.org.apache.xml.internal.security.Init;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CustomerCart implements Initializable {
    public TableColumn productName;
    public TableColumn price;
    public TableColumn rate;
    public TableColumn imageC;
    public TableView<Product> tableProducts;
    public Label productInfo;
    public Label actionInfo;
    public Label totalPrice;
    public Label number;

    Customer customer = (Customer) Account.getOnlineAccount();

    public void clickTable(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();

        if (tableProducts.getSelectionModel().getSelectedItem() == null) {
            setActionInfo("Not Item Selected.", true);
            return;
        }
        Product product = tableProducts.getSelectionModel().getSelectedItem();

        productInfo.setText(product.toString());
        int first = customer.getCart().get(product);
        number.setText(String.valueOf(first));

    }



    public void increase(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();

        if (tableProducts.getSelectionModel().getSelectedItem() == null) {
            setActionInfo("Not Item Selected.", true);
            return;
        }
        try {
            customer.increaseProductAtCart(tableProducts.getSelectionModel().getSelectedItem());
            updateTable();
            setActionInfo("Successful :)", false);
        } catch (InventoryException e) {
            updateTable();
            setActionInfo(e.getMessage(), true);
        }


    }

    public void decrease(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();

        if (tableProducts.getSelectionModel().getSelectedItem() == null) {
            setActionInfo("Not Item Selected.", true);
            return;
        }
        customer.decreaseProductAtCart(tableProducts.getSelectionModel().getSelectedItem());
        updateTable();
        setActionInfo("Successful :)", false);

    }

    private void updateTable() {
        final ObservableList<Product> data = FXCollections.observableArrayList(customer.getCart().keySet());
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        rate.setCellValueFactory(new PropertyValueFactory<Product, String>("averageOfProduct"));
        imageC.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("imageView"));
        tableProducts.setItems(data);
        actionInfo.setText("");
        productInfo.setText("");
        number.setText("");
        totalPrice.setText(String.valueOf(customer.getTotalPriceOFCart()));

    }

    public void goToPaymentPage(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("ReceiveInfoPage", "Receive Info Page", false);

    }

    private void setActionInfo(String message, boolean isError) {
        if (isError) {
            actionInfo.setTextFill(Color.RED);
        }
        else {
            actionInfo.setTextFill(Color.GREEN);
        }
        actionInfo.setText(message);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTable();

        totalPrice.setText(String.valueOf(customer.getTotalPriceOFCart()));
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();

        Main.doBack();
    }
}
