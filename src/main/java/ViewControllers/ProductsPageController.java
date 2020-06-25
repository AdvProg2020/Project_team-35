package ViewControllers;

import Controller.OffBoss;
import Controller.ProductBoss;
import Main.Main;
import Model.Category;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
    public TextField nameFilter;
    public TextField companyFilter;
    public TextField priceFilter;
    public CheckBox inventoryFilter;
    public Label problemOfPublicFilter;
    public TextArea privateAttributes;

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("MainMenu", "main menu", true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }

    public void update() {
        final ObservableList<Category> data = FXCollections.observableArrayList(Category.getAllCategories());
        categoryName.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryName"));
        categoryProductsNum.setCellValueFactory(new PropertyValueFactory<Category, String>("size"));
        table.setItems(data);

    }
    private Category category;

    public void click(MouseEvent mouseEvent) {
        Object object = table.getSelectionModel().selectedItemProperty().get();
        int index = table.getSelectionModel().selectedIndexProperty().get();
         category = Category.categoryFinder(object);
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText(category.getCategoryName() + " information");
        String result = "attributes : \n";
        for (String attribute : category.getSpecialAttributes()) {
            result += attribute + "\n";
        }
        errorAlert.setContentText(result);
        errorAlert.showAndWait();
        createProductsTable(category);

    }

    public void createProductsTable(Category category) {

        final ObservableList<Product> data = FXCollections.observableArrayList(category.getCategoryProducts());
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        rate.setCellValueFactory(new PropertyValueFactory<Product, String>("averageOfProduct"));
        image.setCellValueFactory(new PropertyValueFactory<Product, Image>("image"));
        tableProducts.setItems(data);

    }

    public void productPage(MouseEvent mouseEvent) throws IOException {
        Object object = tableProducts.getSelectionModel().selectedItemProperty().get();
        int index = tableProducts.getSelectionModel().selectedIndexProperty().get();
        Product product = Product.productFinder(object);
        if (product != null) {
            Product.setOnlineProduct(product);
            Main.setRoot("ProductPage", "product page", false);
        }
    }

    public void filterPublics(MouseEvent mouseEvent) {

        if (tableProducts.getItems().isEmpty()){
            problemOfPublicFilter.setText("you need products to filter");
            problemOfPublicFilter.setTextFill(Paint.valueOf("red"));
            return;
        }
        HashMap<String , String> filters = new HashMap<>();
        if (!nameFilter.getText().isEmpty()){
            filters.put("Name",nameFilter.getText());
        }
        if (!priceFilter.getText().isEmpty() && priceFilter.getText().matches("^\\d+-\\d+$")){
            filters.put("Price",priceFilter.getText());
        }
        if (inventoryFilter.isSelected()){
            filters.put("Inventory","");
        }
        if (!companyFilter.getText().isEmpty()){
            filters.put("Company",companyFilter.getText());
        }
        tableProducts.getItems().clear();
        ArrayList<Product> newProducts = OffBoss.filtering(category.getCategoryProducts(),filters);
        final ObservableList<Product> data = FXCollections.observableArrayList(newProducts);
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        rate.setCellValueFactory(new PropertyValueFactory<Product, String>("averageOfProduct"));
        image.setCellValueFactory(new PropertyValueFactory<Product, Image>("image"));
        tableProducts.setItems(data);

    }

    public void filterPrivate(MouseEvent mouseEvent) {
        String[] inputsForFilter = privateAttributes.getText().split("\n");
        String[] fields = new String[category.getSpecialAttributes().size()];
        HashMap<String,String> filterFields = new HashMap<>();
        category.getSpecialAttributes().toArray(fields);
        for (int i = 0; i < inputsForFilter.length && i<category.getSpecialAttributes().size(); i++) {
            if (!inputsForFilter[i].isEmpty()){
                filterFields.put(fields[i],inputsForFilter[i]);
            }
        }
     ArrayList<Product> newProducts =   ProductBoss.filterPrivates(filterFields,category.getCategoryProducts());
        tableProducts.getItems().clear();
        final ObservableList<Product> data = FXCollections.observableArrayList(newProducts);
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        rate.setCellValueFactory(new PropertyValueFactory<Product, String>("averageOfProduct"));
        image.setCellValueFactory(new PropertyValueFactory<Product, Image>("image"));
        tableProducts.setItems(data);

    }
}
