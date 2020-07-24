package ViewControllers;

import Main.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuctionPageController  {
    public TextField extraAmount;
    public Label problem;



    public void enterToChatRoom(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("ChatRoom","chat room",false);
    }

    public void addAmountOfOffer(MouseEvent mouseEvent) throws IOException {
        if (!extraAmount.getText().matches("^\\d+\\.?\\d*$")){
            problem.setText("invalid format");
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }
        String request = "addMoneyToAuction,"+extraAmount.getText();
        String response = Main.sendAndGetMessage(request);
        if (response.equalsIgnoreCase("S")){
            problem.setTextFill(Paint.valueOf("green"));
        }else {
            problem.setTextFill(Paint.valueOf("red"));
        }
        problem.setText(response);


    }
}
