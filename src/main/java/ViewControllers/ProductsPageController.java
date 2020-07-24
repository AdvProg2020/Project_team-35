package ViewControllers;

import Controller.OffBoss;
import Controller.ProductBoss;
import Main.Main;
import Model.Category;
import Model.Customer;
import Model.Product;
import Model.Rate;
import MusicPlayer.MusicPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    public ImageView imageView;
    public Label imageLabel;
    public TableColumn imageC;
    public HBox tableOfStars;
    public ImageView star1;
    public ImageView star2;
    public ImageView star3;
    public ImageView star4;
    public ImageView star5;
    public ListView listOfFilterSubs;
    private Category category;
    private static ArrayList<Category> categories = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("MainMenu", "main menu", true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            update();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update() throws IOException, ClassNotFoundException {
        ArrayList<Category> array = (ArrayList<Category>) Main.sendAndGetObjectFromServer("GetProducts");
        categories = array;
        final ObservableList<Category> data = FXCollections.observableArrayList(array);
        categoryName.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryName"));
        categoryProductsNum.setCellValueFactory(new PropertyValueFactory<Category, String>("size"));
        table.setItems(data);

    }


    public void click(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        Object object = table.getSelectionModel().selectedItemProperty().get();
        int index = table.getSelectionModel().selectedIndexProperty().get();
        for (Category category1 : categories) {
            if (category1.equals(object))
                category = category1;
        }
        // category = Category.categoryFinder(object);
         if (category == null) return;
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText(category.getCategoryName() + " information");
        String result = "attributes : \n";
        for (String attribute : category.getSpecialAttributes()) {
            result += attribute + "\n";
        }
        errorAlert.setContentText(result);
        errorAlert.showAndWait();
        createProductsTable(category);
        listOfFilterSubs.getItems().clear();
        prepareListOfFilterSubs();

    }
    private void prepareListOfFilterSubs(){
        for (String s : category.getSpecialAttributes()) {
            listOfFilterSubs.getItems().add(s);
        }
    }

    public void createProductsTable(Category category) {
        products = category.getCategoryProducts();
        final ObservableList<Product> data = FXCollections.observableArrayList(products);
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        rate.setCellValueFactory(new PropertyValueFactory<Product, String>("averageOfProduct"));
        imageC.setCellValueFactory(new PropertyValueFactory<Product,ImageView>("imageView"));
        tableProducts.setItems(data);
    }

    public void productPage(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        MusicPlayer.getInstance().playButtonMusic();
        Object object = tableProducts.getSelectionModel().selectedItemProperty().get();
        int index = tableProducts.getSelectionModel().selectedIndexProperty().get();
        Product product=null;
        for (Product product1 : products) {
            if (product1.equals(object))
                product = product1;
        }
       // Product product = Product.productFinder(object);
        if (product != null) {
            Product.setOnlineProduct(product);
            String response = (String) Main.sendAndGetObjectFromServer("GetOnlineProductOfProductsPage,"+product.getProductId());
          //  System.out.println(imageLabel.isVisible());
       //    if (imageLabel.isVisible()) {
               Main.setRoot("ProductPage", "product page", false);
         //  }else {
           //    imageLabel.setVisible(true);
             //  imageLabel.setText("image of product status");
              // prepareScoresGraphicMode(product);
           //}
        }
    }
    private void prepareScoresGraphicMode(Product product){
        Image starYellow = new Image("./Resources/yellow.jpg");
        Image starBlank = new Image("./Resources/blank.jpg");
        star1.setImage(starBlank);
        star2.setImage(starBlank);
        star3.setImage(starBlank);
        star4.setImage(starBlank);
        star5.setImage(starBlank);
        if (product.getAverageOfRates()>0){
            star1.setImage(starYellow);
        }
        if (product.getAverageOfRates()>20 ){
            star2.setImage(starYellow);

        }if (product.getAverageOfRates()>40 ){
            star3.setImage(starYellow);

        }if (product.getAverageOfRates()>60 ){
            star4.setImage(starYellow);

        } if (product.getAverageOfRates()>80){
            star5.setImage(starYellow);

        }
    }

    public void filterPublics(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
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
        imageC.setCellValueFactory(new PropertyValueFactory<Product,ImageView>("imageView"));
        tableProducts.setItems(data);

    }

    public void filterPrivate(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
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
        imageC.setCellValueFactory(new PropertyValueFactory<Product,ImageView>("imageView"));
        tableProducts.setItems(data);

    }

    public void doBack(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack();
    }
}
