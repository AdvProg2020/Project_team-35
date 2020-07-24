package ViewControllers;

import Main.Main;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class DownloadPageController {
    public Label result;
    public TextField productID;

    public void sendRequest(MouseEvent mouseEvent) throws IOException {
        if (!productID.getText().matches("^\\d+$")){
            result.setTextFill(Paint.valueOf("red"));
            result.setText("invalid number");
            return;
        }
        String response = Main.sendAndGetMessage("giveMeSeller,"+productID.getText());
    }
}
