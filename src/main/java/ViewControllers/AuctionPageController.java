package ViewControllers;

import Main.Main;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;

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
        if (response.startsWith("S")){
            problem.setTextFill(Paint.valueOf("green"));
            problem.setText("successful now you have "+response.substring(response.indexOf(",")+1));
        }else {
            problem.setTextFill(Paint.valueOf("red"));
            problem.setText(response);
        }



    }
}
