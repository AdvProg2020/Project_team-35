package ViewControllers;

import Main.Main;
import MusicPlayer.MusicPlayer;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddFileController implements Initializable {
    public TextField address;
    public Label problem;
    public AnchorPane pane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button browse = new Button("Browse");
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter png = new FileChooser.ExtensionFilter("png", "*.*");
       // FileChooser.ExtensionFilter jpg = new FileChooser.ExtensionFilter("jpg", "*.jpg");
        fc.getExtensionFilters().addAll(png);
        browse.setOnAction(e->{
            MusicPlayer.getInstance().playButtonMusic();
            address.setText(fc.showOpenDialog(pane.getScene().getWindow()).getAbsolutePath());
        });
        pane.getChildren().add(browse);

        browse.setTranslateY(189);
        browse.setTranslateX(400);
    }

    public void confirm(MouseEvent mouseEvent) throws IOException {
        if (address.getText().equalsIgnoreCase("")){
            problem.setTextFill(Paint.valueOf("red"));
            problem.setText("invalid address");
            return;
        }
        String response = Main.sendAndGetMessage("addFile,"+address.getText());
        Main.setRoot("SellerPage","seller page",false);
    }
}
