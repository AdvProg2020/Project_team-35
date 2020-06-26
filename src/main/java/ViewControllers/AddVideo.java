package ViewControllers;

import Main.Main;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class AddVideo {
    public TextField url;
    public MediaView media;
    public Label problem;

    public void confirm(MouseEvent mouseEvent) throws IOException {

        if (media.getMediaPlayer()==null){
            problem.setText("upload successfully");
            problem.setTextFill(Paint.valueOf("green"));
            Media video = new Media(url.getText());
            MediaPlayer mediaPlayer = new MediaPlayer(video);
            media.setMediaPlayer(mediaPlayer);
            return;
        }else {
            Main.setRoot("SellerPage","seller page",true);
        }
    }
}
