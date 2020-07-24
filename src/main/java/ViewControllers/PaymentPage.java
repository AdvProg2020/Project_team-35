package ViewControllers;

import Controller.CustomerBoss;
import Main.Main;
import Model.Account;
import Model.Customer;
import Model.NoMoneyInCustomerPocket;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentPage implements Initializable {
    public Label actionInfo;
    public RadioButton pocket;
    public RadioButton bank;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateScreen();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateScreen() throws IOException, ClassNotFoundException {
        Customer customer = (Customer) Main.sendAndGetObjectFromServer("GetOnlineAccount");

        if (bank.isSelected() && pocket.isSelected()){
            actionInfo.setTextFill(Paint.valueOf("red"));
            actionInfo.setText("you cant select both");
            return;
        }else if (!bank.isSelected() && !pocket.isSelected()){
            actionInfo.setText("no way is selected");
            actionInfo.setTextFill(Paint.valueOf("red"));
            return;}
        else if (pocket.isSelected()) {
            String isMoneyEnough = (Main.sendAndGetMessage("doPayment1"));

            if (isMoneyEnough.equalsIgnoreCase("false")) {
                setActionErrorInfo("your money is not enough! the purchase wasn't successful.");
            }
            else if (isMoneyEnough.equalsIgnoreCase("true")){
                actionInfo.setTextFill(Color.GREEN);
                actionInfo.setText("purchase successfully done!");
            }else {
                actionInfo.setTextFill(Color.RED);
                actionInfo.setText(isMoneyEnough);
            }
        }
        else Main.sendAndGetMessage("doPayment2");
    }

    public void setActionErrorInfo(String message) {
        actionInfo.setText(message);
        actionInfo.setTextFill(Color.RED);
    }

    public void goToParentPageButtonClick(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("CustomerPage", "Customer Page", false);
    }
}
