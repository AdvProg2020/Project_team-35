package ViewControllers;

import Controller.AccountBoss;
import Controller.Exceptions.NullProduct;
import Controller.Exceptions.SoldProductsCanNotHaveChange;
import Controller.Exceptions.ThisIsNotYours;
import Controller.ProductBoss;
import Controller.SellerBoss;
import Main.Main;
import Model.Account;
import Model.Category;
import Model.Product;
import Model.Seller;
import MusicPlayer.MusicPlayer;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
    public TextField productIdForRemoveThat;
    public Label problemOfRemovingProduct;
    public DatePicker startDateOfAuction;
    public DatePicker finalDateOfAuction;
    public Label auctionProblem;
    public TextField auctionProductId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.updateValuesOnScreen();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateValuesOnScreen() throws IOException, ClassNotFoundException {
        typeLabel.setTextFill(Paint.valueOf("green"));
        Seller onlineAccount = (Seller) Main.sendAndGetObjectFromServer("GetOnlineAccount");
        usernameLabel.setText(onlineAccount.getUsername());
        nameField.setText(onlineAccount.getFirstName());
        lastNameField.setText(onlineAccount.getLastName());
        phoneNumberField.setText(onlineAccount.getPhoneNumber());
        emailField.setText(onlineAccount.getEmail());
        passwordField.setText(onlineAccount.getPassword());
        company.setText(onlineAccount.getCompanyName());

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

    public void updateProfileDate(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        MusicPlayer.getInstance().playButtonMusic();
        Seller seller = (Seller) Main.sendAndGetObjectFromServer("GetOnlineAccount");
        String companyName = company.getText();
        Main.sendMessageToServer("updateCompany,"+companyName);
        List<String> parameters = Arrays.asList("firstName", "lastName", "email", "phoneNumber", "password");
        List<String> values = Arrays.asList(nameField.getText(), lastNameField.getText(), emailField.getText(), phoneNumberField.getText(), passwordField.getText());
        for (int i = 0; i < 5; i++) {

                if (parameters.get(i).equalsIgnoreCase("email") && !values.get(i).matches("^(\\w+)@(\\w+).(\\w+)$")) {
                    information.setText("email invalid format");
                    information.setTextFill(Color.RED);
                    return;
                } else if (parameters.get(i).equalsIgnoreCase("phoneNumber") && !values.get(i).matches("^\\d+$")) {
                    information.setText("phone invalid format");
                    information.setTextFill(Color.RED);
                    return;
                } else {
                    String response = Main.sendAndGetMessage("sellerEditPersonalInfo,"+parameters.get(i)+"+"+values.get(i));
                    if (response.equalsIgnoreCase("S")){
                        information.setTextFill(Color.GREEN);
                        information.setText("Successful :)");
                        this.updateValuesOnScreen();
                    }else {
                        information.setText(response);
                        information.setTextFill(Color.RED);
                        return;
                    }
                }

        }

    }

    public void logoutClick(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        MusicPlayer.getInstance().playButtonMusic();
     String response =   Main.sendAndGetMessage("logout");
        Main.setRoot("MainMenu","main menu",false);
    }

    public void viewSalesHistory(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("SalesHistory", "sales history", false);
    }

    public void companyInfo(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("Company info");
        Seller seller = (Seller) Account.getOnlineAccount();
        errorAlert.setContentText(seller.getCompanyName());
        errorAlert.showAndWait();
    }

    public void goToMain(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("MainMenu", "main menu", false);
    }

    public void creditShow(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("your credit");
        Seller seller = (Seller) Account.getOnlineAccount();
        errorAlert.setContentText(String.valueOf(seller.getMoney()));
        errorAlert.showAndWait();
    }

    public void showListOfSoldProducts(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
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
        MusicPlayer.getInstance().playButtonMusic();
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
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("ProductsPage","products page", false);

    }

    public void addProduct(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("AddProduct","add", false);

    }

    public void viewOffs(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("ViewOffs","view off", false);
    }

    public void removeProduct(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        Seller seller = (Seller)Account.getOnlineAccount();
        if (!productIdForRemoveThat.isVisible()){

            if (seller.getSalableProducts().size()==0){
                problemOfRemovingProduct.setText("you dont have any thing yet");
                problemOfRemovingProduct.setTextFill(Paint.valueOf("pink"));
                return;
            }
            productIdForRemoveThat.setVisible(true);
            return;
        }else {
            if (!productIdForRemoveThat.getText().matches("^\\d+$")){
                problemOfRemovingProduct.setText("invalid format");
                problemOfRemovingProduct.setTextFill(Paint.valueOf("red"));
                return;
            }
            try {
                SellerBoss.removeProduct(productIdForRemoveThat.getText(),(Seller)Account.getOnlineAccount());
                problemOfRemovingProduct.setTextFill(Paint.valueOf("green"));
                problemOfRemovingProduct.setText("successfully deleted");
                if (seller.getSalableProducts().size()==0){
                    productIdForRemoveThat.setVisible(false);
                }
            } catch (ThisIsNotYours | SoldProductsCanNotHaveChange | NullProduct thisIsNotYours) {
                problemOfRemovingProduct.setTextFill(Paint.valueOf("red"));
                problemOfRemovingProduct.setText(thisIsNotYours.getMessage());
                return;
            }
        }
    }

    public void changePic(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("AddPic","picture of account",false);
    }


    public void createAuction(MouseEvent mouseEvent) throws IOException {
       Date start = new Date(startDateOfAuction.getValue().toEpochDay());
       Date finalTime = new Date(finalDateOfAuction.getValue().toEpochDay());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = dateFormat.format(start);
        String finalTimeString = dateFormat.format(finalTime);
       String username = usernameLabel.getText();
       String productId = auctionProductId.getText();
       if (!productId.matches("^\\d+$")){
           auctionProblem.setText("invalid format of id");
           auctionProblem.setTextFill(Paint.valueOf("red"));
           return;
       }
       String response = Main.sendAndGetMessage("makeAuction,"+username+"-"+startTime+"+"+finalTimeString+"#"+auctionProductId.getText());
       if (response.equalsIgnoreCase("S")){
           auctionProblem.setTextFill(Paint.valueOf("green"));
           auctionProblem.setText("successfully made");
       }else {
           auctionProblem.setTextFill(Paint.valueOf("red"));
           auctionProblem.setText("unsuccessfully");
       }
    }
}
