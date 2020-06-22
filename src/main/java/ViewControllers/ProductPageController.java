package ViewControllers;

import Controller.CustomerBoss;
import Controller.ProductBoss;
import Model.Account;
import Model.Customer;
import Model.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {

    public Label productNameLabel;
    public ImageView imageView;
    public TextField score;
    public TextArea review;
    public TextField title;
    public Label offPriceOfProduct;
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
        offPriceOfProduct.setText(String.valueOf(product.getPriceWithOffEffect()));
        offPriceOfProduct.setTextFill(Paint.valueOf("blue"));


    }

    public void addToCart(MouseEvent mouseEvent) {
    }

    public void confirmReview(MouseEvent mouseEvent) {
        ProductBoss.makeComment(review.getText(),title.getText(),product,customer);
    }
}
