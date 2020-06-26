package ViewControllers;

import Main.Main;
import Model.Account;
import Model.Customer;
import Model.DiscountCode;
import MusicPlayer.MusicPlayer;
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

public class CustomerDiscountCodesPage implements Initializable {
    public TableView<DiscountCode> discountCodesTable;
    public TableColumn<DiscountCode, Double> percentColumn;
    public TableColumn<DiscountCode, String> codeColumn;
    public TableColumn<DiscountCode, String> startDateColumn;
    public TableColumn<DiscountCode, String> finalDateColumn;
    public Label remainedInfo;

    public void clickDiscountCodesTable(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        if (discountCodesTable.getSelectionModel().getSelectedItem() != null) {
            DiscountCode discountCode = discountCodesTable.getSelectionModel().getSelectedItem();
            for (Customer customer : discountCode.getIncludedBuyersAndUseFrequency().keySet()) {
                if (customer.equals(Account.getOnlineAccount())) {
                    int remained = discountCode.getAvailableUseFrequent() - discountCode.getIncludedBuyersAndUseFrequency().get(customer);
                    remainedInfo.setText("Remained Uses: " + remained);
                    remainedInfo.setTextFill(Color.GREEN);
                }
            }
        }
        else {
            remainedInfo.setText("Not Selected Item.");
            remainedInfo.setTextFill(Color.RED);
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack();
    }

    ObservableList<DiscountCode> observableList = FXCollections.observableArrayList();
    Customer customer = (Customer) Account.getOnlineAccount();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        observableList.clear();
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("Code"));
        percentColumn.setCellValueFactory(new PropertyValueFactory<>("DiscountPercent"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("StartDateSimpleString"));
        finalDateColumn.setCellValueFactory(new PropertyValueFactory<>("FinalDateSimpleString"));

        observableList.addAll(customer.getDiscountCodes());
        discountCodesTable.setItems(observableList);
    }
}
