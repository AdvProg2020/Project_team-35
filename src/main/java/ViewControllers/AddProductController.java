package ViewControllers;

import Controller.Exceptions.ThereIsNotCategoryWithNameException;
import Controller.SellerBoss;
import Main.Main;
import Model.Account;
import Model.Seller;
import MusicPlayer.MusicPlayer;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AddProductController  {
    public Label problem;
    
    public TextField categoryName;
   
    public TextArea attributes;
    public ListView listOfAttributesName;
    public TextArea decription;
    public TextField name;
    public TextField inventory;
    public TextField price;
    public TextField company;
    public ImageView image;
    private boolean userSawCategoriesAttributesList;


    public void confirm(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        if (!userSawCategoriesAttributesList){
            problem.setTextFill(Paint.valueOf("red"));
            problem.setText("you should have a true category");
            return;
        }
        if (!checkValidityOfInputs()){
            return;
        }

        HashMap<String , String> attributesOfProduct = getSpecialInputs();
        SellerBoss.addRequestProduct(name.getText(),price.getText(),inventory.getText(),attributesOfProduct,company.getText(),categoryName.getText(),(Seller)Account.getOnlineAccount(),decription.getText());
        Main.setRoot("SellerPage","seller page", false);

    }
    private HashMap<String,String> getSpecialInputs(){
        String input = attributes.getText();
        String[] arrayOfInputs = input.split("\n");
        HashMap<String,String> result = new HashMap<>();
        int numberOfAttributes = listOfAttributesName.getItems().size();
        for (int i = 0; i < arrayOfInputs.length; i++) {
            if (i==numberOfAttributes)
                break;
            String type = (String) listOfAttributesName.getItems().get(i);
            result.put(type,arrayOfInputs[i]);
        }
        return result;

    }
    private boolean checkValidityOfInputs(){
        if (!price.getText().matches("^(\\d+)(.?)(\\d*)$")){
            problem.setTextFill(Paint.valueOf("red"));

            problem.setText("invalid format of price");
            return false;
        }else if (!inventory.getText().matches("^(\\d+)(.?)(\\d*)$")){
            problem.setTextFill(Paint.valueOf("red"));

            problem.setText("invalid format of inventory");
            return false;
        }
        return true;
    }

    public void showAttributes(ActionEvent actionEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        try {
           ArrayList<String> specials =  SellerBoss.getWithNameOfCategoryItsSpecials(categoryName.getText());
            listOfAttributesName.getItems().addAll(specials);
            userSawCategoriesAttributesList = true;
        } catch (ThereIsNotCategoryWithNameException e) {
            problem.setText(e.getMessage());
            problem.setTextFill(Paint.valueOf("red"));
            listOfAttributesName.getItems().clear();
            userSawCategoriesAttributesList = false;
            return;
        }
    }
}
