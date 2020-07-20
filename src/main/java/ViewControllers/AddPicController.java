/*
package ViewControllers;

import Main.Main;
import Model.Account;
import MusicPlayer.MusicPlayer;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPicController implements Initializable {
    public ImageView image1;
    public ImageView image3;
    public ImageView image2;
    public ImageView image4;
    public ImageView image5;
    public ImageView image6;

    private Account account;
    public void image1Choose(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        account.setAccountImage(image1.getImage());
        Main.doBack();

    }

    public void image3Choose(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        account.setAccountImage(image3.getImage());
        Main.doBack();

    }

    public void image2Choose(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        account.setAccountImage(image2.getImage());
        Main.doBack();

    }

    public void image4Choose(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        account.setAccountImage(image4.getImage());
        Main.doBack();

    }

    public void image6Choose(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        account.setAccountImage(image6.getImage());
        Main.doBack();

    }

    public void image5Choose(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        account.setAccountImage(image5.getImage());
        Main.doBack();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image1Pic = new Image("./Resources/1.jpg");
        Image image2Pic = new Image("./Resources/2.jpg");
        Image image3Pic = new Image("./Resources/3.jpg");
        Image image4Pic = new Image("./Resources/4.jpg");
        Image image5Pic = new Image("./Resources/5.jpg");
        Image image6Pic = new Image("./Resources/7.jpg");

        image1.setImage(image1Pic);
        image2.setImage(image2Pic);
        image3.setImage(image3Pic);
        image4.setImage(image4Pic);
        image5.setImage(image5Pic);
        image6.setImage(image6Pic);

        account = Account.getOnlineAccount();


    }
}

 */
