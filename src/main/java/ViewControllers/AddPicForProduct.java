package ViewControllers;

import Main.Main;
import Model.Product;
import MusicPlayer.MusicPlayer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class AddPicForProduct {
    public TextField address;
    public ImageView imageView;
    public Label problem;

    public void confirm(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();

        if (imageView.getImage()==null){
            try {
                FileInputStream fileInputStream = new FileInputStream(address.getText());
                Image image = new Image(fileInputStream);
                imageView.setImage(image);
                problem.setTextFill(Paint.valueOf("green"));
                problem.setText("successfully put image for product");
                Product product = Product.getWhoWantsPic();
                product.setProductImage(image);
            }catch (IOException e){
                problem.setText("invalid url for image");
                problem.setTextFill(Paint.valueOf("red"));
                return;
            }
        }else {
            Main.setRoot("AddVideoForProduct","add video",true);
        }
    }
}
