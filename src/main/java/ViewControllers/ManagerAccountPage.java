package ViewControllers;

import Controller.AccountBoss;
import Controller.Exceptions.InvalidNumber;
import Controller.Exceptions.NotValidFieldException;
import Main.Main;
import Model.Account;
import Model.Manager;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerAccountPage implements Initializable {
    public Label usernameLabel;
    public TextField nameField;
    public TextField lastNameField;
    public TextField phoneNumberField;
    public TextField emailField;
    public PasswordField passwordField;
    public Label information;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.updateValuesOnScreen();
    }

    private void updateValuesOnScreen() {
        Account onlineAccount  = Account.getOnlineAccount();
        usernameLabel.setText(onlineAccount.getUsername());
        nameField.setText(onlineAccount.getFirstName());
        lastNameField.setText(onlineAccount.getLastName());
        phoneNumberField.setText(onlineAccount.getPhoneNumber());
        emailField.setText(onlineAccount.getEmail());
        passwordField.setText(onlineAccount.getPassword());
    }

    public void updateProfileDate(MouseEvent mouseEvent) {
        List<String> parameters = Arrays.asList("firstName", "lastName", "email", "phoneNumber", "password");
        List<String> values = Arrays.asList(nameField.getText(), lastNameField.getText(), emailField.getText(), phoneNumberField.getText(), passwordField.getText());
        for (int i = 0; i < 5; i++) {
            try {
                AccountBoss.startEditPersonalField(parameters.get(i), values.get(i), Account.getOnlineAccount());
            } catch (Exception e) {
                information.setText(e.getMessage());
                information.setTextFill(Color.RED);
                return;
            }
        }
        information.setTextFill(Color.GREEN);
        information.setText("Successful :)");
        this.updateValuesOnScreen();
    }

    public void logoutClick(MouseEvent mouseEvent) throws IOException {
        Main.doBack();
        AccountBoss.logout(Account.getOnlineAccount());
    }

    public void goToRequestsPage(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("RequestsPage", "RequestsPage", false);
    }

    public void goToUsersPage(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("UsersManagingPage", "Users Managing Page", false);
    }

    public void goToProductsManagingPage(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("ManagingProducts", "Managing Products", false);
    }

    public void goToCreateNewManagerPage(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("CreateNewManagerPage", "Create Manager Account", false);
    }

    public void goToManageCategoriesPage(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("ManageCategoriesPage", "Manage Categories", false);
    }
}
