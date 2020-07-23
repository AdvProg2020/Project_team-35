package ViewControllers;

import Controller.AccountBoss;
import Controller.Exceptions.RepeatedUserName;
import Controller.ManagerBoss;
import Main.Main;
import MusicPlayer.MusicPlayer;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;

public class CreateSupporterPage {
    public TextField username;
    public TextField firstName;
    public PasswordField password;
    public TextField lastName;
    public TextField email;
    public TextField phoneNumber;
    public Label actionInfo;

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.doBack();
    }

    public void createButtonClick(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        HashMap<String, String> data = new HashMap<>();
        data.put("type", "supporter");
        data.put("username", username.getText());

        if (username.getText().matches("^(\\w+)$")) {
            try {
                Main.sendMessageToServer("MRequestsCheckSupporterUserName-" + username.getText());
                String response = Main.getMessageFromServer();
                if (response.equalsIgnoreCase("Ok")) {
                    if (checkValidityOfInputs()) {
                        data.put("password", password.getText());
                        data.put("name", firstName.getText());
                        data.put("family", lastName.getText());
                        data.put("email address", email.getText());
                        data.put("phone number", phoneNumber.getText());
                        Main.sendMessageToServer("MRequestsRegisterSupporter");
                        Main.sendObjectToServer(data);
                        actionInfo.setTextFill(Color.GREEN);
                        actionInfo.setText("Successful :)");
                    }
                }
                else {
                    setActionErrorInfo("Repeated Username :(");
                }

            } catch (IOException repeatedUserName) {
                setActionErrorInfo(repeatedUserName.getMessage());
            }
        }
        else {
            setActionErrorInfo("Invalid Username Format.");
        }
    }


    private boolean checkValidityOfInputs() {
        if (!password.getText().matches("^(\\w+)$")) {
            setActionErrorInfo("Invalid Password Format.");
            return false;
        }
        else if (!email.getText().matches("^(\\S+)@(\\S+)\\.(\\S+)$")) {
            setActionErrorInfo("Invalid Email Format.");
            return false;
        }
        else if (!phoneNumber.getText().matches("^(\\d+)$")) {
            setActionErrorInfo("Invalid PhoneNumber Format.");
            return false;
        }
        else return true;
    }

    private void setActionErrorInfo(String message) {
        actionInfo.setText(message);
        actionInfo.setTextFill(Color.RED);
    }
}
