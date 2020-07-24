package ViewControllers;

import Main.Main;
import MusicPlayer.MusicPlayer;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupporterPage implements Initializable {
    public TextArea receiveArea;
    public TextArea sendArea;

    public void sendClick(MouseEvent mouseEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void logoutClick(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        String response =  Main.sendAndGetMessage("logout");
        Main.doBack();
    }
}
