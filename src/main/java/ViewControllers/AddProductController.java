package ViewControllers;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public class AddProductController {
    public CheckBox check;
    public Label problem;

    public void confirm(MouseEvent mouseEvent) {
        if (!check.isSelected()){
            problem.setText("you should check maybe this category has special attributes");
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }
    }

    public void select(ActionEvent actionEvent) {
        if (check.isSelected()){

        }else {

        }
    }
}
