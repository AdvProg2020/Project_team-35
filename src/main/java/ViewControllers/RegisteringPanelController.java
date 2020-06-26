package ViewControllers;

import Controller.AccountBoss;
import Main.Main;
import MusicPlayer.MusicPlayer;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RegisteringPanelController {

    public void startLogin(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("LoginPage", "login", false);
    }

    public void startRegister(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("RegisterPage","register", false);
    }
}
