package ViewControllers;

import Controller.SellerBoss;
import Main.Main;
import Model.*;
import MusicPlayer.MusicPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class viewSalesHistory implements Initializable {
    public TableView table;
    public GridPane pane;

    public void back(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.update();
    }

    public void update(){

        Seller seller = (Seller) Account.getOnlineAccount();

        TableColumn tableColumn1 = new TableColumn("logId");
        TableColumn tableColumn = new TableColumn("OrderNumber");
        table.getColumns().addAll(tableColumn,tableColumn1);
        final ObservableList<SellLog> data = FXCollections.observableArrayList(SellerBoss.showHistoryOfSalesForGraphic((Seller)Account.getOnlineAccount()));
        tableColumn.setCellValueFactory(new PropertyValueFactory<SellLog, String>("orderNumber"));
        tableColumn1.setCellValueFactory(new PropertyValueFactory<SellLog,String>("logId"));

        table.setItems(data);


    }

    public void clickItems(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        Object object =  table.getSelectionModel().selectedItemProperty().get();
        int index = table.getSelectionModel().selectedIndexProperty().get();
        String name = table.getSelectionModel().getSelectedItem().toString();
        Seller seller = (Seller) Account.getOnlineAccount();
        SellLog sellLog = seller.findSellLogForGraphic(name);
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("details of sell log with id "+sellLog.getLogId()+" and order number "+ sellLog.getOrderNumber());
       String result = "";
        for (Product product : sellLog.getSoldProducts()) {
            result+=product.getName()+"\n";
        }
        result+= String.valueOf("off discount : "+sellLog.getOffDiscountAmount());
        errorAlert.setContentText(result);
        errorAlert.showAndWait();
    }
}
