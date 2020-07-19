package ViewControllers;

import Controller.CustomerBoss;
import Controller.Exceptions.DiscountIsNotForYou;
import Controller.Exceptions.DiscountNotExist;
import Controller.Exceptions.DontHaveMinimumPriceOfCartForDiscount;
import Main.Main;
import Model.Account;
import Model.Customer;
import MusicPlayer.MusicPlayer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;

public class CheckDiscountCodePage {
    public TextField discountCode;
    public Label actionInfo;

    public void back(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack();
    }

    private void setActionErrorInfo(String message) {
        actionInfo.setText(message);
        actionInfo.setTextFill(Color.RED);
    }

    public void doneButtonClick(MouseEvent mouseEvent) throws IOException{
            if (discountCode.getText().matches("^\\S+$")) {
                try {
                    CustomerBoss.useDiscountCode((Customer) Account.getOnlineAccount(), discountCode.getText());
                    Main.setRoot("PaymentPage", "Payment", false);
                } catch (DiscountNotExist | DiscountIsNotForYou | DontHaveMinimumPriceOfCartForDiscount discountException) {
                    setActionErrorInfo(discountException.getMessage());
                }
            }
            else if (discountCode.getText().equalsIgnoreCase("end")) {
                CustomerBoss.dontUseDiscountCode((Customer) Account.getOnlineAccount());
                Main.setRoot("PaymentPage", "Payment", false);
            }
            else if (discountCode.getText().isEmpty()) {
                setActionErrorInfo("Invalid discount code!");
            }
    }
}
