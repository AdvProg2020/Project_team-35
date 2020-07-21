package ViewControllers;

import Controller.AccountBoss;
import Controller.Exceptions.ExistenceOfUserWithUsername;
import Controller.Exceptions.LoginWithoutLogout;
import Controller.Exceptions.PasswordValidity;
import Main.Main;
import Model.Account;
import Model.Customer;
import Model.Manager;
import Model.Seller;
import MusicPlayer.MusicPlayer;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class LoginPage {
    public TextField username;
    public PasswordField password;
    public Label problem;

    public void confirm(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        String request = "Login," + username.getText() + "-" + password.getText() + "+";

        Account account = Account.getAccountWithUsername(username.getText());
        try {
            AccountBoss.checkLoginWithLogOut(username.getText());
            Account.setOnlineAccount(account);
            Account.setIsThereOnlineUser(true);
        } catch (LoginWithoutLogout existenceOfUserWithUsername) {
            problem.setText(existenceOfUserWithUsername.getMessage());
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }
        String response = Main.sendAndGetMessage(request);

        if (response.equalsIgnoreCase("goToManagerAccountPage")) {
           Main.setRoot("ManagerAccountPage", "Manager Account Page", false);
        } else if (response.equalsIgnoreCase("goToSellerPage")) {
            Main.setRoot("SellerPage", "seller page", false);
        } else if (response.equalsIgnoreCase("goToCustomerPage")) {
            Main.setRoot("CustomerPage", "customer page", false);
        } else if (response.equalsIgnoreCase("goToMainMenu"))
            Main.setRoot("MainMenu", "main menu", false);
        else {
            problem.setText(response);
            problem.setTextFill(Paint.valueOf("red"));
        }
    }

    public void backClick(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("MainMenu", "main menu", false);
    }
}
