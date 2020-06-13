package ViewControllers;

import Controller.SellerBoss;
import Main.Main;
import Model.Account;
import Model.Product;
import Model.Seller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class viewSalesHistory implements Initializable {
    public TableView table;

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("SellerPage","seller page");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.update();
    }

    public void update(){
        TableColumn tableColumn = new TableColumn("products");
        table.getColumns().addAll(tableColumn);
        final ObservableList<String> data = FXCollections.observableArrayList(SellerBoss.showHistoryOfSales((Seller)Account.getOnlineAccount()));
        tableColumn.setCellValueFactory(new PropertyValueFactory<String, String>("products"));

        table.setItems(data);

    }
}
