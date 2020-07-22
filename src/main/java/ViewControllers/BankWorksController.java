package ViewControllers;

import Main.Main;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class BankWorksController {
    public TextField usernameForToken;
    public TextField passwordForToken;
    public Label problemOfToken;

    public void getToken(MouseEvent mouseEvent) throws IOException {
        String response = Main.sendAndGetMessage("getToken,"+usernameForToken.getText()+"-"+passwordForToken.getText());
        if (response.equalsIgnoreCase("invalid username or password")){
            problemOfToken.setTextFill(Paint.valueOf("red"));
        }else {
            problemOfToken.setTextFill(Paint.valueOf("green"));
        }
        problemOfToken.setText(response);
    }
}
