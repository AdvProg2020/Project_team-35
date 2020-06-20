package ViewControllers;

import Controller.Exceptions.ThereIsNotCategoryWithNameException;
import Controller.SellerBoss;
import Model.Category;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditProductPage implements Initializable {
    public TextArea categoryAttributes;
    public TextField categoryName;
    public Label problem;
    public TextArea inputAttributes;
    public TextField company;
    public TextField name;
    public TextField price;
    public TextField inventory;

    public void categoryAttributesShower(ActionEvent actionEvent) {
        ArrayList<String> attributes = new ArrayList<>();
        try {
           attributes = SellerBoss.getWithNameOfCategoryItsSpecials(categoryName.getText());
        } catch (ThereIsNotCategoryWithNameException e) {
            problem.setText(e.getMessage());
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }
        String text = "";
        for (String attribute : attributes) {
            text+=attribute+"\n";
        }
        categoryAttributes.setText(text);
        problem.setText("category successfully changed");
        problem.setTextFill(Paint.valueOf("green"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Product product = Product.getOnlineProduct();
        name.setText(product.getName());
        categoryName.setText(product.getCategory().getCategoryName());
        price.setText(String.valueOf(product.getPrice()));
        inventory.setText(String.valueOf(product.getInventory()));
        company.setText(product.getCompany());
        ArrayList<String> attributes = new ArrayList<>();
        try {
            attributes = SellerBoss.getWithNameOfCategoryItsSpecials(categoryName.getText());
        } catch (ThereIsNotCategoryWithNameException e) {
            problem.setText(e.getMessage());
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }
        String text = "";
        for (String attribute : attributes) {
            text+=attribute+"\n";
        }
        categoryAttributes.setText(text);
    }

    public void confirm(MouseEvent mouseEvent) {
        HashMap<String,String> allChanges = new HashMap<>();
        Product product = Product.getOnlineProduct();
        if (!checkValidityOfInputs()){
            return;
        }
        if (!product.getName().equalsIgnoreCase(name.getText())){
            allChanges.put("name",name.getText());
        }
        if (!product.getCompany().equalsIgnoreCase(company.getText())){
            //allChanges.put()
        }
        if (!product.getCategory().getCategoryName().equalsIgnoreCase(categoryName.getText())){

        }
        if (product.getPrice()!=Double.parseDouble(price.getText())){

        }
        if (product.getInventory()!=Double.parseDouble(inventory.getText())){

        }
        if (inputAttributes.getText()!=null){

        }
    }
    private boolean checkValidityOfInputs(){
        if (!price.getText().matches("^(\\d+)(.?)(\\d*)$")){
            problem.setTextFill(Paint.valueOf("red"));

            problem.setText("invalid format of price");
            return false;
        }else if (!inventory.getText().matches("^(\\d+)(.?)(\\d*)$")){
            problem.setTextFill(Paint.valueOf("red"));

            problem.setText("invalid format of price");
            return false;
        }
        return true;

    }
}
