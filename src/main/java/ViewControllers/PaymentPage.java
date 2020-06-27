package ViewControllers;

import Controller.CustomerBoss;
import Main.Main;
import Model.Account;
import Model.Customer;
import Model.NoMoneyInCustomerPocket;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentPage implements Initializable {
    public Label actionInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateScreen();
    }

    public void updateScreen() {
        try {
            if (!CustomerBoss.doPayment((Customer) Account.getOnlineAccount()))
                setActionErrorInfo("your money is not enough! the purchase wasn't successful.");
            else {
                actionInfo.setTextFill(Color.GREEN);
                actionInfo.setText("purchase successfully done!");
            }
        } catch (NoMoneyInCustomerPocket noMoneyInCustomerPocket) {
            setActionErrorInfo(noMoneyInCustomerPocket.getMessage());
        }
    }

    public void setActionErrorInfo(String message) {
        actionInfo.setText(message);
        actionInfo.setTextFill(Color.RED);
    }

    public void goToParentPageButtonClick(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("CustomerCart", "Customer Cart", false);
    }
}
