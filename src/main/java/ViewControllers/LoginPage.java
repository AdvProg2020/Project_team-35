package ViewControllers;

import Controller.AccountBoss;
import Controller.Exceptions.ExistenceOfUserWithUsername;
import Controller.Exceptions.LoginWithoutLogout;
import Controller.Exceptions.PasswordValidity;
import Main.Main;
import Model.Account;
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
        try {
            AccountBoss.checkUsernameExistenceInLogin(username.getText());
        } catch (ExistenceOfUserWithUsername | LoginWithoutLogout existenceOfUserWithUsername) {
           problem.setText(existenceOfUserWithUsername.getMessage());
           problem.setTextFill(Paint.valueOf("red"));
           return;
        }
        try {
            AccountBoss.checkPasswordValidity(username.getText(),password.getText());
        } catch (PasswordValidity passwordValidity) {
            problem.setTextFill(Paint.valueOf("red"));
            problem.setText(passwordValidity.getMessage());
            return;
        }
        AccountBoss.startLogin(username.getText(),password.getText());
        Main.setRoot("MainMenu","main menu");
    }
}
