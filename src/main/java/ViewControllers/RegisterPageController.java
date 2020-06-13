package ViewControllers;

import Controller.AccountBoss;
import Controller.Exceptions.MoreThanOneManagerException;
import Controller.Exceptions.RepeatedUserName;
import Controller.Exceptions.RequestProblemNotExistManager;
import Main.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.HashMap;

public class RegisterPageController {
    public TextField company;
    public TextField type;
    public TextField username;
    public PasswordField password;
    public TextField name;
    public TextField lastName;
    public TextField email;
    public TextField phone;
    public Label problem;

    public void confirm(MouseEvent mouseEvent) throws IOException {
        HashMap<String,String> allPersonalInfo = new HashMap<>();
        try {
            AccountBoss.firstStepOfRegistering(type.getText(),username.getText());
        } catch ( RequestProblemNotExistManager | RepeatedUserName | MoreThanOneManagerException e) {
            problem.setText(e.getMessage());
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }
        if (!type.getText().matches("^(customer|seller)$")){
            problem.setTextFill(Paint.valueOf("red"));
            problem.setText("invalid type");
            return;
        }
        createAllPersonalInfo(allPersonalInfo);
        if (checkValidityOfData(allPersonalInfo)) {
            AccountBoss.makeAccount(allPersonalInfo);
            Main.setRoot("MainMenu","main menu");
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
        allPersonalInfo.put("type",type.getText());
        addFields(allPersonalInfo, username, password, name, lastName, email, phone);
        if (type.getText().equalsIgnoreCase("seller"))
            allPersonalInfo.put("company name",company.getText());
    }

    static void addFields(HashMap<String, String> allPersonalInfo, TextField username, TextField password, TextField name, TextField lastName, TextField email, TextField phone) {
        allPersonalInfo.put("username", username.getText());
        allPersonalInfo.put("password", password.getText());
        allPersonalInfo.put("name", name.getText());
        allPersonalInfo.put("family", lastName.getText());
        allPersonalInfo.put("email address", email.getText());
        allPersonalInfo.put("phone number", phone.getText());
    }


    public void back(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("RegisteringPanel","register or login");
    }


    public void showCompanyOrNot(ActionEvent actionEvent) {
        if (type.getText().equalsIgnoreCase("seller")){
            company.setDisable(false);
        }
        else {
            company.setDisable(true);
        }
    }
}
