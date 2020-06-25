package ViewControllers;

import Controller.CustomerBoss;
import Controller.Exceptions.NullProduct;
import Controller.Exceptions.ProductIsFinished;
import Controller.Exceptions.ProductsCompareNotSameCategories;
import Controller.ProductBoss;
import Model.Account;
import Model.Customer;
import Model.Product;
import MusicPlayer.MusicPlayer;
import Views.RegisteringPanel;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {

    public Label productNameLabel;
    public ImageView imageView;
    public TextField score;
    public TextArea review;
    public TextField title;
    public Label offPriceOfProduct;
    public TextArea reviewsList;
    public Label problem;
    public TextArea compareResult;
    public TextField compareProductId;
    public TextField numberOfAddingtoCart;
    public TextArea attributes;
    private Product product;
    private Customer customer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }
    private void update(){
        product = Product.getOnlineProduct();
        if (Account.getOnlineAccount() instanceof Customer){
            customer = (Customer) Account.getOnlineAccount();
            if (CustomerBoss.findOutSpecialProductThatIsForACustomerOrNot(product,customer)){
                score.setVisible(true);
            }
        }
        Product product = Product.getOnlineProduct();
        productNameLabel.setText(product.getName());
        if (product.getPriceWithOffEffect()!= -1) {
            offPriceOfProduct.setText(String.valueOf(product.getPriceWithOffEffect()));
        }else {
            offPriceOfProduct.setText(String.valueOf(product.getPrice()));
        }
        offPriceOfProduct.setTextFill(Paint.valueOf("blue"));
        HashMap<String,String> attri = ProductBoss.showAttributes(product);
        String result = "";
        for (String s : attri.keySet()) {
            result+= s+"            -->         "+attri.get(s)+"\n";
        }
        attributes.setText(result);


    }

    public void addToCart(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        if (!numberOfAddingtoCart.isVisible()){
            numberOfAddingtoCart.setVisible(true);
            return;
        }else {
            if (!numberOfAddingtoCart.getText().matches("^\\d+$")){
                problem.setText("number of adding to cart format is invalid");
                problem.setTextFill(Paint.valueOf("red"));
                return;
            }

            if (Account.getOnlineAccount()==null){
                System.err.println("first login");
                problem.setTextFill(Paint.valueOf("blue"));
                problem.setText("first login");
                return;
            }else if (!(Account.getOnlineAccount() instanceof Customer)){
                problem.setTextFill(Paint.valueOf("blue"));
                problem.setText("process is for customer");
                return;
            }
            else if (product.getInventory()!=0){
                Customer customer = (Customer) Account.getOnlineAccount();
                customer.getListOFProductsAtCart().put(product,Integer.parseInt(numberOfAddingtoCart.getText()));
                problem.setText("successfully added to cart");
                problem.setTextFill(Paint.valueOf("green"));
            }else{
                problem.setTextFill(Paint.valueOf("blue"));
                problem.setText("product is finished");
                return;
            }
        }
    }

    public void confirmReview(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        if (customer==null){
            problem.setTextFill(Paint.valueOf("red"));
            problem.setText("just customers can add comment");
            return;
        }
        ProductBoss.makeComment(review.getText(),title.getText(),product,customer);
        problem.setText("successfully made a comment");
        problem.setTextFill(Paint.valueOf("green"));
        review.clear();
        title.clear();
    }

    public void showListOfReviews(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        System.out.println(product.getName());
        HashMap<String,String> listOfComments = ProductBoss.showComments(product);
        String result = "";
        for (String s : listOfComments.keySet()) {
            result+= s+"    :   "+listOfComments.get(s) +"\n";
        }
        reviewsList.setText(result);
    }

    public void compare(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        if (!compareProductId.isVisible()){
            compareProductId.setVisible(true);
            compareResult.clear();
            return;
        }else {
            try {
                if (!compareProductId.getText().matches("\\d+")){
                    problem.setTextFill(Paint.valueOf("red"));
                    problem.setText("invalid format of id");
                    return;
                }
            StringBuilder text =     ProductBoss.compare(compareProductId.getText(),product);
            compareResult.setText(String.valueOf(text));
            } catch (ProductsCompareNotSameCategories | ProductIsFinished | NullProduct productsCompareNotSameCategories) {
                problem.setText(productsCompareNotSameCategories.getMessage());
                problem.setTextFill(Paint.valueOf("red"));
            }
        }
    }
}
