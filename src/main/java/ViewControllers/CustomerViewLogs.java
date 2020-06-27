package ViewControllers;

import Main.Main;
import Model.Account;
import Model.BuyLog;
import Model.Customer;
import Model.Request;
import MusicPlayer.MusicPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerViewLogs implements Initializable {
    public Label info;
    Customer customer = (Customer) Account.getOnlineAccount();
    public TableColumn<BuyLog, Integer> logIdCol;
    public TableView<BuyLog> logsTable;

    public void logsTableClick(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        if (logsTable.getSelectionModel().getSelectedItem() != null) {
            info.setText(logsTable.getSelectionModel().getSelectedItem().getBuyLogInfo());
        }
        else {
            info.setText("Not Selected Item.");
        }
    }

    ObservableList<BuyLog> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logIdCol.setCellValueFactory(new PropertyValueFactory<>("LogId"));
        observableList.addAll(customer.getBuyLogs());
        logsTable.setItems(observableList);
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack();
    }
}
