package ViewControllers;

import MusicPlayer.MusicPlayer;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class ViewCategoryController {
    public TableView table;

    public void back(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
    }
}
