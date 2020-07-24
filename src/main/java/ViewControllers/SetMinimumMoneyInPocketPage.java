package ViewControllers;

import Main.Main;
import Model.Manager;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;

public class SetMinimumMoneyInPocketPage {
    public TextField textField;
    public Label actionInfo;

    public void confirmButtonClick(MouseEvent mouseEvent) {
        Manager.setMinimumMoneyInPocket(Double.parseDouble(textField.getText()));
        actionInfo.setTextFill(Color.GREEN);
        actionInfo.setText("Successfully Done! :)");
    }

    public void backButtonClick(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("ManagerAccountPage", "Manager Account", false);
    }
}
