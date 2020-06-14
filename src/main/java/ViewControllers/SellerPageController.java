package ViewControllers;

import Controller.AccountBoss;
import Controller.SellerBoss;
import Main.Main;
import Model.Account;
import Model.Product;
import Model.Seller;
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

public class SellerPageController implements Initializable {


    public Label usernameLabel;
    public TextField nameField;
    public TextField lastNameField;
    public TextField phoneNumberField;
    public TextField emailField;
    public PasswordField passwordField;
    public Label information;
    public TextField company;
    public Label typeLabel;
    public Label errorLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.updateValuesOnScreen();
    }

    private void updateValuesOnScreen() {
        typeLabel.setTextFill(Paint.valueOf("green"));
        Account onlineAccount = Account.getOnlineAccount();
        usernameLabel.setText(onlineAccount.getUsername());
        nameField.setText(onlineAccount.getFirstName());
        lastNameField.setText(onlineAccount.getLastName());

        phoneNumberField.setText(onlineAccount.getPhoneNumber());
        emailField.setText(onlineAccount.getEmail());
        passwordField.setText(onlineAccount.getPassword());
        Seller seller = (Seller) Account.getOnlineAccount();
        company.setText(seller.getCompanyName());
    }

    public boolean checkValidity(String type, String input) {
        if (type.equals("email address") || type.equalsIgnoreCase("email")) {
            if (!input.matches("^(\\w+)@(\\w+).(\\w+)$")) {
                errorLabel.setTextFill(Paint.valueOf("red"));
                errorLabel.setText("email is invalid");
            }
            return input.matches("^(\\w+)@(\\w+).(\\w+)$");
        } else if (type.equals("phone number") || type.equalsIgnoreCase("phoneNumber")) {
            if (!input.matches("^\\d+$")) {
                errorLabel.setTextFill(Paint.valueOf("red"));
                errorLabel.setText("phone is invalid");
            }
            return input.matches("^\\d+$");
        }
        return true;
    }

    public void updateProfileDate(MouseEvent mouseEvent) {
        Seller seller = (Seller) Account.getOnlineAccount();
        seller.setCompanyName(company.getText());
        List<String> parameters = Arrays.asList("firstName", "lastName", "email", "phoneNumber", "password");
        List<String> values = Arrays.asList(nameField.getText(), lastNameField.getText(), emailField.getText(), phoneNumberField.getText(), passwordField.getText());
        for (int i = 0; i < 5; i++) {
            try {
                if (parameters.get(i).equalsIgnoreCase("email") && !values.get(i).matches("^(\\w+)@(\\w+).(\\w+)$")) {
                    information.setText("email invalid format");
                    information.setTextFill(Color.RED);
                    return;
                } else if (parameters.get(i).equalsIgnoreCase("phoneNumber") && !values.get(i).matches("^\\d+$")) {
                    information.setText("phone invalid format");
                    information.setTextFill(Color.RED);
                    return;
                } else {
                    AccountBoss.startEditPersonalField(parameters.get(i), values.get(i), Account.getOnlineAccount());
                }
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
        Main.setRoot("SalesHistory", "sales history");
    }

    public void companyInfo(MouseEvent mouseEvent) {
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("Company info");
        Seller seller = (Seller) Account.getOnlineAccount();
        errorAlert.setContentText(seller.getCompanyName());
        errorAlert.showAndWait();
    }

    public void goToMain(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("MainMenu", "main menu");
    }

    public void creditShow(MouseEvent mouseEvent) {
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("your credit");
        Seller seller = (Seller) Account.getOnlineAccount();
        errorAlert.setContentText(String.valueOf(seller.getMoney()));
        errorAlert.showAndWait();
    }

    public void showListOfSoldProducts(MouseEvent mouseEvent) {
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("sold products");
        String result = "";
        Seller seller = (Seller) Account.getOnlineAccount();
        for (String sale : SellerBoss.showHistoryOfSales(seller)) {
            result+=sale+"\n";
        }
        errorAlert.setContentText(result);
        errorAlert.showAndWait();
    }

    public void listOfSalableProducts(MouseEvent mouseEvent) {
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("salable products");
        Seller seller = (Seller) Account.getOnlineAccount();
        String result = "";
        for (Product product : SellerBoss.salableProducts(seller)) {
            result+=product.getName()+" with id "+product.getProductId()+"\n";
        }
        errorAlert.setContentText(result);
        errorAlert.showAndWait();
    }

    public void viewCategory(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("ProductsPage","products page");

    }

    public void addProduct(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("AddProduct","add");

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