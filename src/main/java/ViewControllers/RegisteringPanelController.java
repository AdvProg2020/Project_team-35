package ViewControllers;

import Controller.AccountBoss;
import Main.Main;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RegisteringPanelController {

    public void startLogin(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("LoginPage", "login");
    }

    public void startRegister(MouseEvent mouseEvent) throws IOException {

        Main.setRoot("RegisterPage","register");
    }
}
