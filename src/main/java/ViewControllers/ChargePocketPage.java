package ViewControllers;

import Main.Main;
import Model.Customer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ChargePocketPage {
    public TextField textField;
    public Label actionInfo;
    public Button confirmButton;
    public Button backButton;

    private void setActionErrorInfo(String message) {
        actionInfo.setText(message);
        actionInfo.setTextFill(Color.RED);
    }

    public void confirmButtonClick(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        Customer customer = (Customer) Main.sendAndGetObjectFromServer("GetOnlineAccount");
        double amount = Double.parseDouble(textField.getText());
        if (textField.getText().matches("\\d+")) {
            if (customer.getMoney() < amount)
                setActionErrorInfo("You Don't Have Enough Money In Your Account!");
            else {
                customer.setMoney(customer.getMoney() - amount);
                customer.setPocket(customer.getPocket() + amount);
                actionInfo.setTextFill(Color.GREEN);
                actionInfo.setText("Pocket Successfully Charged :)");
            }
        }
        else setActionErrorInfo("Invalid Input Format!");
    }

    public void backButtonClick(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("CustomerPage", "Customer Page", false);
    }
}
