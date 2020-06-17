package ViewControllers;

import Controller.Exceptions.ThisIsNotYours;
import Controller.Exceptions.ThisOffNotExist;
import Controller.SellerBoss;
import Model.Account;
import Model.Seller;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

public class EditOff {
    public TextField offId;
    public AnchorPane dataPage;
    public Label problem;

    public void showData(ActionEvent actionEvent) {
        if (!offId.getText().matches("^\\d+$")){
            problem.setTextFill(Paint.valueOf("red"));
            problem.setText("invalid off id format");
            return;
        }
        try {
            SellerBoss.checkOffOfSeller((Seller) Account.getOnlineAccount(),Integer.parseInt(offId.getText()));
            dataPage.setVisible(true);
        } catch (ThisOffNotExist | ThisIsNotYours thisOffNotExist) {
            problem.setText(thisOffNotExist.getMessage());
            problem.setTextFill(Paint.valueOf("red"));
        }
    }
}
