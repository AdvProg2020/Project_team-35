package ViewControllers;

import Controller.AccountBoss;
import Main.Main;
import Model.Account;
import Model.Seller;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SellerPageController implements Initializable {



        public Label usernameLabel;
        public TextField nameField;
        public TextField lastNameField;
        public TextField phoneNumberField;
        public TextField emailField;
        public PasswordField passwordField;
        public Label information;
    public TextField company;


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
            Seller seller =(Seller) Account.getOnlineAccount();
            company.setText(seller.getCompanyName());
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
            AccountBoss.logout(Account.getOnlineAccount());
            Main.tree.pop();
            Main.setRoot(Main.tree.peek(), Main.tree.peek());

    }

    public void viewSalesHistory(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("SalesHistory","sales history");
    }

    public void companyInfo(MouseEvent mouseEvent) {
    }

    public void goToMain(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("MainMenu","main menu");
    }
}
/*
 subPages.put("2", viewCompanyInformation());
        subPages.put("3", viewSalesHistory());
        subPages.put("4", viewCredit());
        subPages.put("5", viewCategory());
        subPages.put("6", manageProducts());
        subPages.put("7", addProduct());
        subPages.put("8", removeProduct());
        subPages.put("9", viewOffs());
        subPages.put("1", new RegisteringPanel("registering panel", this));
 */