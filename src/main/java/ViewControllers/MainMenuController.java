package ViewControllers;

import Main.Main;
import Model.Account;
import Model.Seller;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {
    public void userPageEntrance(MouseEvent mouseEvent) throws IOException {
        if (Account.isIsThereOnlineUser()) {
          if (Account.getOnlineAccount() instanceof Seller){
              Main.setRoot("SellerPage","seller page");
          }
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
