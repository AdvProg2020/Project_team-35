package ViewControllers;

import Controller.AccountBoss;
import Main.Main;
import Model.Account;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class UserPageController {

    public Button button;

    public void registerOrLogin(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("RegisteringPanel","register/login", false);
    }

    public void showRegisterAndLoginButton(ActionEvent actionEvent) {
        if (Account.isIsThereOnlineUser()){
         button.setVisible(false);
        }
    }
}
