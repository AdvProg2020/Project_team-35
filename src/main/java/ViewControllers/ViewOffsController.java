package ViewControllers;

import Controller.Exceptions.*;
import Controller.SellerBoss;
import Main.Main;
import Model.Account;
import Model.Off;
import Model.Seller;
import MusicPlayer.MusicPlayer;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.HashMap;

public class ViewOffsController {

    public TextField offIdOfView;
    public Label problem;
    public TextArea dataOfOff;
    public AnchorPane editOffPagePart;
    public TextField offEditId;
    public TextField startDate;
    public TextField finalDate;
    public TextField percentage;
    public TextField max;

    public void viewOff(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        Seller seller = (Seller)Account.getOnlineAccount();
        if (seller.getSellerOffs()==null){
            problem.setTextFill(Paint.valueOf("red"));
            problem.setText("you dont have off");
            return;
        }
        if (editOffPagePart.isVisible() || offEditId.isVisible()){
            editOffPagePart.setVisible(false);
            offEditId.setVisible(false);
            offIdOfView.setVisible(true);
            return;
        }
        if (!offIdOfView.isVisible()){
            offIdOfView.setVisible(true);
            return;
        }
       else {
            if (!offIdOfView.getText().matches("^\\d+$")){
                problem.setTextFill(Paint.valueOf("red"));
                problem.setText("invalid format of id");
                return;
            }

            String offInfo = "";
            try {
              offInfo    =   SellerBoss.viewOff((Seller) Account.getOnlineAccount(),offIdOfView.getText());
              editOffPagePart.setVisible(false);
              offEditId.setVisible(false);
              dataOfOff.setVisible(true);
                System.out.println(offInfo);
              dataOfOff.setText(offInfo);
            } catch (ThisIsNotYours | ThisOffNotExist thisIsNotYours) {
                problem.setText(thisIsNotYours.getMessage());
                problem.setTextFill(Paint.valueOf("red"));
                return;
            }

        }

    }

    public void addOff(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("AddOffPage","add off", false);
    }

    public void editOff(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        Seller seller = (Seller) Account.getOnlineAccount();
        if (seller.getSellerOffs()==null){
            problem.setText("you dont have off");
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }

        if (!offEditId.isVisible()){
            offEditId.setVisible(true);
            offIdOfView.setVisible(false);
            return;
        }
        if (!editOffPagePart.isVisible()){
            dataOfOff.setVisible(false);
            editOffPagePart.setVisible(true);
            return;
        }
        if (!offEditId.getText().matches("^\\d+$")){
            problem.setTextFill(Paint.valueOf("red"));
            problem.setText("invalid format of id");
            return;
        }
        if (!checkValidityOfOffEditData()){
            return;
        }
        HashMap<String,String> changes = new HashMap<>();
        changes.put("maximumAmountOfOff",max.getText());
        changes.put("offPercent",percentage.getText());
        changes.put("finalDate",finalDate.getText());
        changes.put("startDate",startDate.getText());

            offIdOfView.setVisible(false);
            dataOfOff.setVisible(false);
            editOffPagePart.setVisible(true);

        try {
            SellerBoss.editOff(seller, Off.getOffById(Integer.parseInt(offEditId.getText())),changes);
            problem.setTextFill(Paint.valueOf("green"));
            problem.setText("successfully send request for manager");
        } catch (TimeLimit | InputStringExceptNumber | ThisIsNotReadyForEdit | ThisIsNotYours timeLimit) {
            problem.setText(timeLimit.getMessage());
            problem.setTextFill(Paint.valueOf("red"));
        }


    }
    private boolean checkValidityOfOffEditData(){
        if (!startDate.getText().matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$")){
            problem.setText("invalid start time");
            problem.setTextFill(Paint.valueOf("red"));
            return false;
        }else if (!finalDate.getText().matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$")){
            problem.setText("invalid final time");
            problem.setTextFill(Paint.valueOf("red"));
            return false;
        }else if (!percentage.getText().matches("^\\d+\\.?\\d*$")){
            problem.setText("invalid percent");
            problem.setTextFill(Paint.valueOf("red"));
            return false;
        }else if (!max.getText().matches("^\\d+\\.?\\d*$")){
            problem.setText("invalid max");
            problem.setTextFill(Paint.valueOf("red"));
            return false;
        }
        return true;
    }


    public void back(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack();
    }
}
