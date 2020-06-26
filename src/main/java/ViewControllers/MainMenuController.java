package ViewControllers;

import Main.Main;
import Model.Account;
import Model.Seller;
import MusicPlayer.MusicPlayer;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainMenuController {
    public void userPageEntrance(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        if (Account.isIsThereOnlineUser()) {
          if (Account.getOnlineAccount() instanceof Seller){
              Main.setRoot("SellerPage","seller page", false);
          }
        } else {
            Main.setRoot("UserPage", "user page", false);
        }
    }

    public void productsPageEntrance(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("ProductsPage","products page",false);
    }

    public void offsEntrance(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("OffsPage","offs", false);
    }



    public void back(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack("First Page");
    }
}
