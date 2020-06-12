package ViewControllers;

import Controller.AccountBoss;
import Controller.ManagerBoss;
import Main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FirstPageController {
    public void start(MouseEvent mouseEvent) {
        if (ManagerBoss.weHaveManagerOrNot()){
            String fxml = "MainMenu";
            try {
                Main.setRoot(fxml,"main menu");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            String fxml = "ManagerRegister";
            try {
                Main.setRoot(fxml,"manager registering part");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void end(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
