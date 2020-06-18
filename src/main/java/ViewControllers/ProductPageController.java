package ViewControllers;

import Controller.CustomerBoss;
import Controller.ProductBoss;
import Model.Account;
import Model.Customer;
import Model.Product;
import Model.Seller;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {

    public Label productNameLabel;
    public ImageView imageView;
    public TextField score;
    public TextArea review;
    public TextField title;
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
        productNameLabel.setText(Product.getOnlineProduct().getName());


    }

    public void addToCart(MouseEvent mouseEvent) {
    }

    public void confirmReview(MouseEvent mouseEvent) {
        ProductBoss.makeComment(review.getText(),title.getText(),product,customer);
    }
}
