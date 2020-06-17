package ViewControllers;

import Main.Main;
import Model.Category;
import Model.Off;
import Model.Product;
import Model.Seller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    public void goToMainMenu(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("MainMenu","main menu");
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
        for (Off activeOff : Off.getAllActiveOffs()) {
            final ObservableList<Product> data = FXCollections.observableArrayList(activeOff.getIncludedProducts());
            name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
            price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
            rate.setCellValueFactory(new PropertyValueFactory<Product, String>("averageOfProduct"));
            sTime.setCellValueFactory(new PropertyValueFactory<Off,String>("startDate"));
            fTime.setCellValueFactory(new PropertyValueFactory<Off,String>("finalDate"));
            image.setCellValueFactory(new PropertyValueFactory<Product, Image>("image"));

            table.setItems(data);
        }

    }
}
