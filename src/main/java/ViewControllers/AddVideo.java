package ViewControllers;

import Main.Main;
import Model.Product;
import MusicPlayer.MusicPlayer;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddVideo implements Initializable {
    public TextField url;
    public MediaView media;
    public Label problem;
    public AnchorPane pane;

    public void confirm(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        if (media.getMediaPlayer()==null){
            problem.setText("upload successfully \n click on video to play");
            problem.setTextFill(Paint.valueOf("green"));
            String address = "file:///"+url.getText();
            address = address.replace("\\","//");
            Media video = new Media(address);
            MediaPlayer mediaPlayer = new MediaPlayer(video);
            media.setMediaPlayer(mediaPlayer);
            Product.getWhoWantsPic().setMedia(video);
            return;
        }else {
            media.getMediaPlayer().stop();
            Main.setRoot("SellerPage","seller page",true);
        }
    }

    public void play(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();

        if (!media.getMediaPlayer().isAutoPlay()){
            media.getMediaPlayer().setAutoPlay(true);
            media.getMediaPlayer().play();
        }else {
            media.getMediaPlayer().stop();
            media.getMediaPlayer().setAutoPlay(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button browse = new Button("Browse");
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter png = new FileChooser.ExtensionFilter("MP4", "*.MP4");
        FileChooser.ExtensionFilter jpg = new FileChooser.ExtensionFilter("MKV", "*.MKV");
        fc.getExtensionFilters().addAll(png,jpg);
        browse.setOnAction(e->{
            MusicPlayer.getInstance().playButtonMusic();
            url.setText(fc.showOpenDialog(pane.getScene().getWindow()).getAbsolutePath());
        });
        pane.getChildren().add(browse);
        browse.setTranslateY(135);
        browse.setTranslateX(500);
    }
}
