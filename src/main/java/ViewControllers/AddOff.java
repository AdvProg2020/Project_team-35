package ViewControllers;

import Controller.Exceptions.*;
import Controller.SellerBoss;
import Main.Main;
import Model.Account;
import Model.Seller;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AddOff {
    public TextField startDate;
    public TextField finalDate;
    public TextField percentage;
    public TextField maximum;
    public TextArea productsIds;
    public TextField offId;
    public Label problem;

    public void confirm(MouseEvent mouseEvent) throws IOException {

        ArrayList<Integer> ids = new ArrayList<>();
        if (checkProductsId()){
            for (String s : productsIds.getText().split(" ")) {
                ids.add(Integer.parseInt(s));
            }
        }else {
            problem.setTextFill(Paint.valueOf("red"));
            problem.setText("invalid format of product id");
            return;
        }
        if (!startDate.getText().matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$") || !finalDate.getText().matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$")){
            problem.setText("date format problem");
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }

        try {
            SellerBoss.addOff(ids,(Seller) Account.getOnlineAccount(),startDate.getText(),finalDate.getText(),percentage.getText(),maximum.getText());
            Main.setRoot("SellerPage","seller page", false);
        } catch (ParseException | ThisIsNotYours | TimeLimit | InvalidNumber | InputStringExceptNumber | NullProduct | JustOneOffForEveryProduct e) {
            problem.setText(e.getMessage());
            problem.setTextFill(Paint.valueOf("red"));
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
