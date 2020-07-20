package ViewControllers;

import Controller.AccountBoss;
import Controller.Exceptions.InvalidNumber;
import Controller.Exceptions.NotValidFieldException;
import Main.Main;
import Model.Account;
import Model.Customer;
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

public class CustomerPage implements Initializable {
    public Label usernameLabel;
    public TextField nameField;
    public TextField lastNameField;
    public TextField phoneNumberField;
    public TextField emailField;
    public PasswordField passwordField;
    public Label information;
   // public ImageView image;
    public Label balanceLabel;

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
        Customer customer = (Customer) Account.getOnlineAccount();
        balanceLabel.setText(String.valueOf(customer.getMoney()));
      //  if (onlineAccount.getAccountImage()!=null){
         //   image.setImage(onlineAccount.getAccountImage());
       // }
    }

    public void updateProfileDate(MouseEvent mouseEvent) {
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

    public void logoutClick(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack();
        AccountBoss.logout(Account.getOnlineAccount());
    }

    public void goToCustomerDiscountCodes(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("CustomerDiscountCodesPage", "Customer Discount Codes", false);
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


    protected static Matcher getMatcher(String input , String regex){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    public void goToViewBuyLogs(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("CustomerViewLogs", "Buy Logs List", false);
    }

    public void goToCart(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("CustomerCart", "Cart", false);
    }

    public void goToProductsPage(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("ProductsPage", "Products Page", false);
    }
}
