package ViewControllers;

import Main.Main;
import Model.Account;
import Model.Manager;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BankWorksController implements Initializable {
    public TextField usernameForToken;
    public TextField passwordForToken;
    public Label problemOfToken;
    public TextField transactionToken;
    public Label resultOfTransaction;
    public Button exitButton;
    public TextField transferToken;
    public TextField receiptType;
    public TextField money;
    public TextField sourceID;
    public TextField destID;
    public TextField description;
    public Label transferResult;
    public TextField billID;
    public Label paymentResult;

    public void getToken(MouseEvent mouseEvent) throws IOException {
        String response = Main.sendAndGetMessage("getToken,"+usernameForToken.getText()+"-"+passwordForToken.getText());
        if (response.equalsIgnoreCase("invalid username or password")){
            problemOfToken.setTextFill(Paint.valueOf("red"));
        }else {
            problemOfToken.setTextFill(Paint.valueOf("green"));
        }
        problemOfToken.setText(response);
    }

    public void exit(MouseEvent mouseEvent) throws IOException {
        Main.sendMessageToServer("exit");
        System.exit(0);
    }

    public void getTransaction(MouseEvent mouseEvent) throws IOException {
        String response = Main.sendAndGetMessage("transactionResult,"+transactionToken);
        if (response.equalsIgnoreCase("database error") ||response.equalsIgnoreCase("invalid input") ||response.equalsIgnoreCase("token is invalid") || response.equalsIgnoreCase("token expired")){
            resultOfTransaction.setTextFill(Paint.valueOf("red"));
        }else {
            resultOfTransaction.setTextFill(Paint.valueOf("green"));
        }
        resultOfTransaction.setText(response);

    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("MainMenu","main menu",false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Account account = null;
        try {
            account = (Account) Main.sendAndGetObjectFromServer("GetOnlineAccount");
            if (account instanceof Manager){
                exitButton.setVisible(true);
            }else {
                exitButton.setVisible(false);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void transfer(MouseEvent mouseEvent) throws IOException {
        String request = "transfer,"+"{token,"+transactionToken.getText()+"}{receiptType,"+receiptType.getText()+"}{money,"+money.getText()+"}{sourceID,"+sourceID.getText()+"}{destID,"+destID.getText()+"}{description,"+description.getText()+"}";
        String response = Main.sendAndGetMessage(request);
        if (response.matches("^\\d+$")){
            transferResult.setTextFill(Paint.valueOf("green"));
        }else {
            transferResult.setTextFill(Paint.valueOf("red"));
        }
        transferResult.setText(response);
    }

    public void pay(MouseEvent mouseEvent) throws IOException {
        String checkThisIsOurBill = Main.sendAndGetMessage("ThisIsOUrBill,"+billID.getText());
        if (checkThisIsOurBill.equalsIgnoreCase("done successfully")){
            paymentResult.setTextFill(Paint.valueOf("green"));
        }else {
            paymentResult.setTextFill(Paint.valueOf("red"));
        }
        paymentResult.setText(checkThisIsOurBill);
    }
}