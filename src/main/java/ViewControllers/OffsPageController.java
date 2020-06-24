package ViewControllers;

import Controller.Exceptions.CategoryNull;
import Controller.Exceptions.InvalidNumber;
import Controller.Exceptions.MaxMinReplacement;
import Controller.Exceptions.SellerShouldJustBe;
import Controller.OffBoss;
import Main.Main;
import Model.Category;
import Model.Off;
import Model.Product;
import Model.Seller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OffsPageController implements Initializable {
    public TableColumn name;
    public TableColumn image;
    public TableColumn rate;
    public TableColumn price;
    public TableColumn sTime;
    public TableColumn fTime;
    public TableColumn time;
    public TableColumn discount;
    public TableView table;
    public TextField companyFilterField;
    public TextField nameFilterField;
    public TextField categoryFilterField;
    public TextField inventoryFilterField;
    public TextField PriceFilterField;

    public void goToMainMenu(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("MainMenu","main menu", false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Product > a = new ArrayList<>();
        Seller seller = new Seller("asd","sad","asd","asd","asd","asd","asd");
        Product product = new Product("ads","a",213,seller,2,new Category("asd",null),null,"");
       a.add(product);
        Off off = new Off(LocalDateTime.parse("2021-12-23T23:23:23"),LocalDateTime.now(),a,32,12,seller);
        Product product1 = new Product("ad232s","a",213,seller,2,new Category("asd",null),null,"");

        a.add(product1);
        a.remove(product);
        Off off1 = new Off(LocalDateTime.parse("2021-12-23T23:23:23"),LocalDateTime.now(),a,32,12,seller);


        update();
    }
    public void update(){
        ///
        ///
        ///
        // probably it should be changed
        ///
        ///
        ///
        ArrayList<Product> all = new ArrayList<>();
        for (Off activeOff : Off.getAllActiveOffs()) {
            for (Product product : activeOff.getIncludedProducts()) {
                all.add(product);
            }
        }
            final ObservableList<Product> data = FXCollections.observableArrayList(all);
            name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
            rate.setCellValueFactory(new PropertyValueFactory<Product, String>("averageOfProduct"));
            sTime.setCellValueFactory(new PropertyValueFactory<Product,String>("startDate"));
            fTime.setCellValueFactory(new PropertyValueFactory<Product,String>("finalDate"));
            image.setCellValueFactory(new PropertyValueFactory<Product, Image>("image"));
            discount.setCellValueFactory(new PropertyValueFactory<Product,Double>("priceWithOffEffect"));
            time.setCellValueFactory(new PropertyValueFactory<Product,String>("daysRemind"));

            table.setItems(data);
        }



    public void filterCompany(ActionEvent actionEvent) {
        try {
            OffBoss.addFieldToFilterFields("Company",companyFilterField.getText(),"");
        } catch (CategoryNull | InvalidNumber | MaxMinReplacement | SellerShouldJustBe categoryNull) {
            categoryNull.printStackTrace();
        }
        ArrayList<Product> all = new ArrayList<>();
        for (Off activeOff : Off.getAllActiveOffs()) {
            for (Product product : activeOff.getIncludedProducts()) {
                all.add(product);
            }
        }
        updateTableOfProducts(OffBoss.filterFields(all));


    }
    private void updateTableOfProducts(ArrayList<Product> products){
        table.getItems().clear();
        final ObservableList<Product> data = FXCollections.observableArrayList(products);
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        rate.setCellValueFactory(new PropertyValueFactory<Product, String>("averageOfProduct"));
        sTime.setCellValueFactory(new PropertyValueFactory<Off,String>("startDate"));
        fTime.setCellValueFactory(new PropertyValueFactory<Off,String>("finalDate"));
        image.setCellValueFactory(new PropertyValueFactory<Product, Image>("image"));

        table.setItems(data);
    }

    public void filterName(ActionEvent actionEvent) {
    }

    public void filterCategory(ActionEvent actionEvent) {
    }

    public void filterInventory(ActionEvent actionEvent) {
    }

    public void filterPrice(ActionEvent actionEvent) {
    }
}
