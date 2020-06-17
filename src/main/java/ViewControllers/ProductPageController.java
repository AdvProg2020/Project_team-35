package ViewControllers;

import Model.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductPageController implements Initializable {

    public Label productNameLabel;
    public ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private void update(){
        productNameLabel.setText(Product.getOnlineProduct().getName());
    }

    public void addToCart(MouseEvent mouseEvent) {
    }
}
