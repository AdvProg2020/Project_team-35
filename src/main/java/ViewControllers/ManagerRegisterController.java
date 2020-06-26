package ViewControllers;

import Controller.AccountBoss;
import Controller.Exceptions.MoreThanOneManagerException;
import Controller.Exceptions.RepeatedUserName;
import Controller.Exceptions.RequestProblemNotExistManager;
import Main.Main;
import Model.Account;
import MusicPlayer.MusicPlayer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;

public class ManagerRegisterController {
    public TextField phone;
    public TextField email;
    public TextField lastName;
    public TextField name;
    public TextField password;
    public TextField username;
    public Label problem;

    public void confirm(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        HashMap<String,String> allPersonalInfo = new HashMap<>();
        try {
            AccountBoss.firstStepOfRegistering("manager",username.getText());
        } catch ( RequestProblemNotExistManager | RepeatedUserName | MoreThanOneManagerException e) {
            problem.setText(e.getMessage());
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }
        createAllPersonalInfo(allPersonalInfo);
        if (checkValidityOfData(allPersonalInfo)) {
            AccountBoss.makeAccount(allPersonalInfo);
            Account.setWhoWantsToHavePic(Account.getAccountWithUsername(username.getText()));
           Main.setRoot("AddPicInRegisteringPanel","add pic", false);
        }

    }
    public boolean checkValidityOfData(HashMap<String , String> allPersonalInfo){
        for (String s : allPersonalInfo.keySet()) {
            if (!checkValidity(s,allPersonalInfo.get(s)))
                return false;
        }
        return true;
    }
    public  boolean checkValidity(String type,String input){
        if (type.equals("email address")|| type.equalsIgnoreCase("email")) {
            if (! input.matches("^(\\w+)@(\\w+).(\\w+)$")){
                problem.setTextFill(Paint.valueOf("red"));
                problem.setText("email is invalid");
            }
            return input.matches("^(\\w+)@(\\w+).(\\w+)$");
        } else if (type.equals("phone number")|| type.equalsIgnoreCase("phoneNumber")) {
            if (!input.matches("^\\d+$")){
                problem.setTextFill(Paint.valueOf("red"));
                problem.setText("phone is invalid");
            }
            return input.matches("^\\d+$");
        }
        return true;
    }
    public void createAllPersonalInfo(HashMap<String,String> allPersonalInfo){
        allPersonalInfo.put("type","manager");
        RegisterPageController.addFields(allPersonalInfo, username, password, name, lastName, email, phone);
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.setRoot("FirstPage","start Page", false);

    }
}
