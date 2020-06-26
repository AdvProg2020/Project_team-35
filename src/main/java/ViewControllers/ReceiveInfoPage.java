package ViewControllers;

import Main.Main;
import MusicPlayer.MusicPlayer;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ReceiveInfoPage {
    public javafx.scene.control.TextField phoneNumber;
    public TextField address;
    public javafx.scene.control.Label actionInfo;

    private boolean checkValidityOfInputs() {
        if (!phoneNumber.getText().matches("^(\\d+)$")) {
            setActionErrorInfo("Invalid Phone Number Format!");
            return false;
        }
        else return true;
    }

    private void setActionErrorInfo(String message) {
        actionInfo.setText(message);
        actionInfo.setTextFill(Color.RED);
    }

    public void confirmButtonClick(MouseEvent mouseEvent) {
        if (checkValidityOfInputs()) {
            actionInfo.setTextFill(Color.GREEN);
            actionInfo.setText("Successful :)");
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack();
    }
}
