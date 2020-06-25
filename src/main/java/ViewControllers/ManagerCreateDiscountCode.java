package ViewControllers;

import Controller.Exceptions.DateException;
import Controller.Exceptions.NotExistCustomerWithUserNameException;
import Controller.ManagerBoss;
import Main.Main;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.awt.*;
import java.net.CookieHandler;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ManagerCreateDiscountCode {

    public TextField codeText;
    public TextField percent;
    public TextField maximumDiscountAmount;
    public TextField repeatRate;
    public DatePicker startDate;
    public DatePicker finalDate;
    public TextArea customersUserNames;
    public TextField minimumTotalPrice;
    public CheckBox noMinimumCheckBox;
    public Button createButton;
    public Label information;
    public TextField startTime;
    public TextField finalTime;


    public void createDiscountCode(MouseEvent mouseEvent) {
        if (!checkInputs()) {
            return;
        }
        LocalDateTime startFullDate = LocalDateTime.parse(startDate.getValue().toString() + "T" + startTime.getText());
        LocalDateTime finalFullDate = LocalDateTime.parse(finalDate.getValue().toString() + "T" + finalTime.getText());
        try {
            ManagerBoss.checkStartDateAndFinalDateForDiscountCode(startFullDate, finalFullDate);
        } catch (DateException e) {
            setInformation(e.getMessage(), true);
            return;
        }
        ArrayList<String> includedCustomers = new ArrayList<>();
        Collections.addAll(includedCustomers, customersUserNames.getText().split("\\n"));
        for (String includedCustomer : includedCustomers) {
            try {
                ManagerBoss.checkExistenceOfCustomerUsername(includedCustomer);
            } catch (NotExistCustomerWithUserNameException e) {
                setInformation("Customer With Username " + includedCustomer + "does'nt exist.", true);
                return;
            }
        }
        int repeat = Integer.parseInt(repeatRate.getText());
        double percentage = Double.parseDouble(percent.getText());
        double minimum = Double.parseDouble(minimumTotalPrice.getText());
        double maximum = Double.parseDouble(maximumDiscountAmount.getText());
        String code = codeText.getText();
        ManagerBoss.createDiscountCode(code, finalFullDate, startFullDate, percentage, maximum, repeat, includedCustomers, minimum);
        setInformation("Successfully Created :)", false);

    }

    public void clickNoMinimumCheckBox(ActionEvent actionEvent) {
        minimumTotalPrice.setDisable(noMinimumCheckBox.isSelected());
    }



    private boolean checkInputs() {
        Matcher codeTextMatcher = Pattern.compile("^\\S+$").matcher(codeText.getText());
        if (!codeTextMatcher.matches()) {
            setInformation("Code Text Format is invalid.", true);
            return false;
        }
        Matcher percentMatcher = Pattern.compile("^(\\d{1,2}(\\.\\d+)?)$").matcher(percent.getText());
        if (!percentMatcher.matches()) {
            setInformation("Percent field format is invalid.", true);
            return false;
        }

        Matcher doubleMatcherMaximum = Pattern.compile("^(\\d+(\\.\\d+)?)$").matcher(maximumDiscountAmount.getText());
        if (!doubleMatcherMaximum.matches()) {
            setInformation("Maximum Amount Format is invalid", true);
            return false;
        }
        Matcher intMatcher = Pattern.compile("^\\d+$").matcher(repeatRate.getText());
        if (!intMatcher.matches()) {
            setInformation("Repeat Rate Format is invalid.", true);
            return false;
        }
        Matcher startTimeMatcher = Pattern.compile("^(([0-1][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]$").matcher(startTime.getText());
        if (!startTimeMatcher.matches()) {
            setInformation("Start Time Format is invalid. valid: [h][h]:[m][m]:[s][s]", true);
            return false;
        }
        Matcher finalTimeMatcher = Pattern.compile("^(([0-1][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]$").matcher(finalTime.getText());
        if (!finalTimeMatcher.matches()) {
            setInformation("Final Time Format is invalid. valid: [h][h]:[m][m]:[s][s]", true);
            return false;
        }
        Matcher doubleMatcherMinimumPrice = Pattern.compile("^(\\d+(\\.\\d+)?)$").matcher(minimumTotalPrice.getText());
        if (!doubleMatcherMinimumPrice.matches() && !noMinimumCheckBox.isSelected()) {
            setInformation("Minimum Price Format is invalid.", true);
            return false;
        }


        return true;
      }


    private void setInformation(String message, boolean isError) {
        if (isError) {
            information.setTextFill(Color.RED);
        }
        else {
            information.setTextFill(Color.GREEN);
        }
        information.setText(message);
    }
}
