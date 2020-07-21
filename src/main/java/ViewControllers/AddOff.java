package ViewControllers;

import Main.Main;
import MusicPlayer.MusicPlayer;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.ArrayList;

public class AddOff {
    public TextField startDate;
    public TextField finalDate;
    public TextField percentage;
    public TextField maximum;
    public TextArea productsIds;
    public TextField offId;
    public Label problem;

    public void confirm(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        ArrayList<Integer> ids = new ArrayList<>();
        if (!checkProductsId()){

            problem.setTextFill(Paint.valueOf("red"));
            problem.setText("invalid format of product id");
            return;
        }
        if (!startDate.getText().matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$") || !finalDate.getText().matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$")){
            problem.setText("date format problem");
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }

        String idOfProducts = productsIds.getText();
        String startDataTime  = startDate.getText();
        String finalDateTime = finalDate.getText();
        String max = maximum.getText();
        String percent = percentage.getText()  ;
        String request = "AddOff,"+max+"-"+percent+"+"+idOfProducts+"!"+startDataTime+"$"+finalDateTime;
        String response = Main.sendAndGetMessage(request);

       if (response.equalsIgnoreCase("S"))
            Main.setRoot("SellerPage","seller page", false);
       else {
           problem.setTextFill(Paint.valueOf("red"));
           problem.setText(response);
       }
    }
    private boolean checkProductsId(){
        for (String s : productsIds.getText().split(" ")) {
            if (!s.matches("^\\d+$"))
                return false;
        }
        return true;
    }
}
