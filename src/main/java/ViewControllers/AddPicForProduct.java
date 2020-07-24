package ViewControllers;

import Main.Main;
import Model.Product;
import MusicPlayer.MusicPlayer;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPicForProduct implements Initializable {
    public TextField address;
    public ImageView imageView;
    public Label problem;
    public AnchorPane pane;

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
            }catch (IOException e){
                problem.setText("invalid url for image");
                problem.setTextFill(Paint.valueOf("red"));
                return;
            }
        }else {
            Main.setRoot("AddVideoForProduct","add video",true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Button browse = new Button("Browse");
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter png = new FileChooser.ExtensionFilter("png", "*.png");
        FileChooser.ExtensionFilter jpg = new FileChooser.ExtensionFilter("jpg", "*.jpg");
        fc.getExtensionFilters().addAll(png,jpg);
        browse.setOnAction(e->{
            MusicPlayer.getInstance().playButtonMusic();
            address.setText(fc.showOpenDialog(pane.getScene().getWindow()).getAbsolutePath());
        });
        pane.getChildren().add(browse);

        browse.setTranslateY(189);
        browse.setTranslateX(400);

    }
}
