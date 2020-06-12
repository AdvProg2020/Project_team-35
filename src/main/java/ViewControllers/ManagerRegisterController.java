package ViewControllers;

import Controller.AccountBoss;
import Controller.Exceptions.MoreThanOneManagerException;
import Controller.Exceptions.RepeatedUserName;
import Controller.Exceptions.RequestProblemNotExistManager;
import Main.Main;
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
        HashMap<String,String> allPersonalInfo = new HashMap<>();
        try {
            AccountBoss.firstStepOfRegistering("manager",username.getText());
        } catch (MoreThanOneManagerException | RepeatedUserName | RequestProblemNotExistManager e) {
            problem.setText("invalid username");
            problem.setTextFill(Paint.valueOf("red"));
        }
        createAllPersonalInfo(allPersonalInfo);
        if (checkValidityOfData(allPersonalInfo)) {
            AccountBoss.makeAccount(allPersonalInfo);
           Main.setRoot("MainMenu","main menu");
        }
        else {
            problem.setText("invalid data");
            problem.setTextFill(Paint.valueOf("red"));

        }
    }
    public boolean checkValidityOfData(HashMap<String , String> allPersonalInfo){
        for (String s : allPersonalInfo.keySet()) {
            if (!checkValidity(s,allPersonalInfo.get(s)))
                return false;
        }
        return true;
    }
    public boolean checkValidity(String type,String input){
        if (type.equals("email address")|| type.equalsIgnoreCase("email")) {
            return input.matches("^(\\w+)@(\\w+).(\\w+)$");
        } else if (type.equals("phone number")|| type.equalsIgnoreCase("phoneNumber")) {
            return input.matches("^\\d+$");
        }
        return true;
    }
    public void createAllPersonalInfo(HashMap<String,String> allPersonalInfo){
        allPersonalInfo.put("type","manager");
        allPersonalInfo.put("username",username.getText());
        allPersonalInfo.put("password",password.getText());
        allPersonalInfo.put("name",name.getText());
        allPersonalInfo.put("family",lastName.getText());
        allPersonalInfo.put("email address",email.getText());
        allPersonalInfo.put("phone number",phone.getText());
    }

    public void back(MouseEvent mouseEvent) throws IOException {

            Main.setRoot("FirstPage","start Page");

    }
}
