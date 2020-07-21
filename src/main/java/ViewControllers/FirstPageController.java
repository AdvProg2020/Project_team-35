package ViewControllers;

import Controller.AccountBoss;
import Controller.ManagerBoss;
import Main.Main;
import MusicPlayer.MusicPlayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FirstPageController {
    public void start(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        String response = Main.sendAndGetMessage("B");
        if (response.equalsIgnoreCase("S")){
            String fxml = "MainMenu";
            try {
                Main.setRoot(fxml,"main menu", false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            String fxml = "ManagerRegister";
            try {
                Main.setRoot(fxml,"manager registering part", false);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void end(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        System.exit(0);
    }
}
