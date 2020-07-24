package ViewControllers;

import Controller.AccountBoss;
import Main.Main;
import Model.Account;
import Model.Customer;
import MusicPlayer.MusicPlayer;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
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
    public Label balanceLabel;
    public TextField auctionId;
    public TextField entranceAuctionId;
    public Label addToAuctionProblem;
    public TextField basicPrice;
    public Label enterToAuctionProblem;
    public Label chargePocketProblem;
    public TextField amount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.updateValuesOnScreen();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateValuesOnScreen() throws IOException, ClassNotFoundException {
        Customer onlineAccount = (Customer) Main.sendAndGetObjectFromServer("GetOnlineAccount");
        usernameLabel.setText(onlineAccount.getUsername());
        nameField.setText(onlineAccount.getFirstName());
        lastNameField.setText(onlineAccount.getLastName());
        phoneNumberField.setText(onlineAccount.getPhoneNumber());
        emailField.setText(onlineAccount.getEmail());
        passwordField.setText(onlineAccount.getPassword());
        balanceLabel.setText(String.valueOf(onlineAccount.getMoney()));

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

      String response =  Main.sendAndGetMessage("logout");

        Main.setRoot("MainMenu","main menu",false);
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

    public void showAuctions(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        String response = Main.sendAndGetMessage("showAuctions");
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("auction info");
        errorAlert.setContentText(response);
        errorAlert.showAndWait();
    }

    public void addToAnAuction(MouseEvent mouseEvent) throws IOException {
        if (!auctionId.getText().matches("^\\d+$")){
            addToAuctionProblem.setText("invalid auction id");
            addToAuctionProblem.setTextFill(Paint.valueOf("red"));
            return;
        }else if (!basicPrice.getText().matches("^\\d+\\.?\\d*$")){
            addToAuctionProblem.setText("invalid basic price");
            addToAuctionProblem.setTextFill(Paint.valueOf("red"));
            return;
        }
        String response = Main.sendAndGetMessage("addMeToAuction,"+auctionId.getText()+"-"+basicPrice.getText());
        if (response.equalsIgnoreCase("S")){
            addToAuctionProblem.setTextFill(Paint.valueOf("green"));
            addToAuctionProblem.setText("successfully:)");
        }else {
            addToAuctionProblem.setTextFill(Paint.valueOf("red"));
            addToAuctionProblem.setText(response);
        }

    }

    public void enterToAnAuction(MouseEvent mouseEvent) throws IOException {
        if (!entranceAuctionId.getText().matches("^\\d+$")){
            enterToAuctionProblem.setText("invalid format of number");
            enterToAuctionProblem.setTextFill(Paint.valueOf("red"));
            return;
        }
        String request = "enterToAuction,"+entranceAuctionId.getText();
        String response = Main.sendAndGetMessage(request);
        if (request.equalsIgnoreCase("S")){
            Main.setRoot("AuctionPage","auction page",false);
        }else {
            enterToAuctionProblem.setTextFill(Paint.valueOf("red"));
            enterToAuctionProblem.setText(response);
        }

    }

    public void goToSupportPage(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("CustomerSupportPage", "Online Chat", false);
    }

    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("MainMenu","main menu",false);
    }
}
