package ViewControllers;

import Main.Main;
import Model.Category;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductsPageController implements Initializable {
    public TableColumn categoryProductsNum;
    public TableColumn categoryName;
    public TableView table;
    public TableView tableProducts;
    public TableColumn seller;
    public TableColumn price;
    public TableColumn productName;
    public TableColumn image;
    public TableColumn id;
    public TableColumn rate;

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("MainMenu","main menu");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        ArrayList<String > a = new ArrayList<>();
        a.add("sad");
        Category category = new Category("asd",a);
        Category category1 = new Category("alal",a);
        for (int i = 0; i <100 ; i++) {


            Product product = new Product("das", "Ads", 123, new Seller("dsa", "Asd", "Asd", "ads", "ads", "ASd", "da"), 3, category, null, "");
            category.getCategoryProducts().add(product);
        }

         */
        update();
    }
    public void update(){
        final ObservableList<Category> data = FXCollections.observableArrayList(Category.getAllCategories());
        categoryName.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryName"));
        categoryProductsNum.setCellValueFactory(new PropertyValueFactory<Category,String>("size"));
        table.setItems(data);

    }

    public void click(MouseEvent mouseEvent) {
        Object object =  table.getSelectionModel().selectedItemProperty().get();
        int index = table.getSelectionModel().selectedIndexProperty().get();
        Category category = Category.categoryFinder(object);
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText(category.getCategoryName()+" information");
        String result="attributes : \n";
        for (String attribute : category.getSpecialAttributes()) {
            result+=attribute+"\n";
        }
        errorAlert.setContentText(result);
        errorAlert.showAndWait();
        createProductsTable(category);

    }
    public void createProductsTable(Category category){

        final ObservableList<Product> data = FXCollections.observableArrayList(category.getCategoryProducts());
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product,String>("price"));
        rate.setCellValueFactory(new PropertyValueFactory<Product,String>("averageOfProduct"));
        image.setCellValueFactory(new PropertyValueFactory<Product, Image>("image"));
        tableProducts.setItems(data);

    }
}
