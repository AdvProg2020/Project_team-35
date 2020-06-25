package ViewControllers;

import Controller.CustomerBoss;
import Main.Main;
import Model.Account;
import Model.Customer;
import Model.DiscountCode;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ShowDiscountCodesPage implements Initializable {
    public TableView discountsCodesTable;
    public javafx.scene.control.TableColumn<DiscountCode, String> idCol;
    public javafx.scene.control.TableColumn<DiscountCode, LocalDateTime> dateCol;
    public javafx.scene.control.TableColumn<DiscountCode, Double> percentCol;
    public javafx.scene.control.TableColumn<DiscountCode, Double> maxCol;
    public javafx.scene.control.TableColumn<DiscountCode, Integer> freqCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }

    public void update() {
        ObservableList<DiscountCode> observableList = FXCollections.observableArrayList(CustomerBoss.showDiscountCodes((Customer) Account.getOnlineAccount()));
        observableList.clear();
        idCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("finalDate"));
        percentCol.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        maxCol.setCellValueFactory(new PropertyValueFactory<>("maximumAvailableAmount"));
        freqCol.setCellValueFactory(new PropertyValueFactory<>("availableUseFrequent"));
    }
}
