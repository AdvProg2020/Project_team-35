package ViewControllers;

import Controller.CustomerBoss;
import Model.Account;
import Model.Customer;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewBalance implements Initializable {
    public Label balance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateScreen();
    }

    public void updateScreen() {
        balance.setText(Double.toString(CustomerBoss.showMoney((Customer) Account.getOnlineAccount())));
    }
}
