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
              Main.setRoot("SellerPage","seller page", false);
          }
        } else {
            Main.setRoot("UserPage", "user page", false);
        }
    }

    public void productsPageEntrance(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("ProductsPage","products page",false);
    }

    public void offsEntrance(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("OffsPage","offs", false);
    }



    public void back(MouseEvent mouseEvent) throws IOException {
        Main.doBack("First Page");
    }
}
