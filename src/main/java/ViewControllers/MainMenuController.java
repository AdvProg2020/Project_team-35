package ViewControllers;

import Main.Main;
import Model.Account;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {
    public void userPageEntrance(MouseEvent mouseEvent) throws IOException {
        if (Account.isIsThereOnlineUser()) {
            Main.setRoot("UserPageWithLogin","user page");
        } else {
            Main.setRoot("UserPage", "user page");
        }
    }

    public void productsPageEntrance(MouseEvent mouseEvent) {
    }

    public void offsEntrance(MouseEvent mouseEvent) {
    }



    public void back(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("FirstPage","FirstPage");
    }
}
