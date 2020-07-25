package ViewControllers;

import Controller.AccountBoss;
import Controller.Exceptions.InvalidNumber;
import Controller.Exceptions.NotValidFieldException;
import Main.Main;
import Model.Account;
import Model.Manager;

import MusicPlayer.MusicPlayer;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        try {
            this.updateValuesOnScreen();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateValuesOnScreen() throws IOException, ClassNotFoundException {

        Account onlineAccount = (Account) Main.sendAndGetObjectFromServer("GetOnlineAccount");
        usernameLabel.setText(onlineAccount.getUsername());
        nameField.setText(onlineAccount.getFirstName());
        lastNameField.setText(onlineAccount.getLastName());
        phoneNumberField.setText(onlineAccount.getPhoneNumber());
        emailField.setText(onlineAccount.getEmail());
        passwordField.setText(onlineAccount.getPassword());

    }

    public void updateProfileDate(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        MusicPlayer.getInstance().playButtonMusic();
        if (!checkEmailAlphabet(emailField.getText())) {
            information.setText("Invalid Email Form.");
            information.setTextFill(Color.RED);
            return;
        }
        if (!checkPhoneNumber(phoneNumberField.getText())) {
            information.setText("Invalid Phone Number Form.");
            information.setTextFill(Color.RED);
            return;
        }
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

    public void logoutClick(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        MusicPlayer.getInstance().playButtonMusic();
    String response =     Main.sendAndGetMessage("logout");
        Main.doBack();
    }

    public void goToRequestsPage(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("RequestsPage", "RequestsPage", false);
    }

    public void goToUsersPage(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("UsersManagingPage", "Users Managing Page", false);
    }

    public void goToProductsManagingPage(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("ManagingProducts", "Managing Products", false);
    }

    public void goToCreateNewManagerPage(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("CreateNewManagerPage", "Create Manager Account", false);
    }

    public void goToManageCategoriesPage(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("ManageCategoriesPage", "Manage Categories", false);
    }


    private boolean checkEmailAlphabet(String email) {
        return getMatcher(email, "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
                "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-" +
                "\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2" +
                "[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\" +
                "x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$").matches();
    }


    private boolean checkPhoneNumber(String phoneNumber) {
        return getMatcher(phoneNumber, "^\\d+$").matches();
    }


    protected static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public void goToCreateDiscountCodePage(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("ManagerCreateDiscountCode", "Create Discount Code", false);
    }

    public void goToCreateSupporterPage(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("CreateSupporterPage", "create new Supporter", false);
    }

    public void bankWorks(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("BankWorks","bank",false);
    }
    public void goToSetMinimumMoneyPage(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("SetMinimumMoneyInPocketPage", "Set Minimum Money In Pocket", false);
    }


}
