package ViewControllers;

import Main.Main;
import Model.Account;
import Model.Customer;
import Model.Manager;
import Model.Seller;
import MusicPlayer.MusicPlayer;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class MainMenuController {
    public Label problem;

    public void userPageEntrance(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        MusicPlayer.getInstance().playButtonMusic();
        Account account = (Account) Main.sendAndGetObjectFromServer("GetOnlineAccount");
        if (account!=null) {
          if (account instanceof Seller){
              Main.setRoot("SellerPage","seller page", false);
          }else if (account instanceof Manager){
              Main.setRoot("ManagerPage","manager page", false);
          }else if (account instanceof Customer){
              Main.setRoot("CustomerPage","customer page", false);
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
        Main.doBack();
    }

    public void startPage(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("FirstPage","first page",false);
    }

    public void bankWorksEnterance(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        Account account = (Account) Main.sendAndGetObjectFromServer("GetOnlineAccount");
        if (account==null){
            problem.setText("you need login for this part");
            problem.setTextFill(Paint.valueOf("red"));
        }else {
            Main.setRoot("BankWorks","bank option" , false);
        }
    }
}
