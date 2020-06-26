package ViewControllers;

import Controller.Exceptions.DateException;
import Controller.Exceptions.DiscountNotExist;
import Controller.Exceptions.NotExistCustomerWithUserNameException;
import Controller.ManagerBoss;
import Main.Main;

import Model.Category;
import Model.DiscountCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.awt.*;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ManagerCreateDiscountCode implements Initializable {

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
    public Label selectedCodeData;
    public TableView<DiscountCode> discountCodesTable;
    public TableColumn<DiscountCode, String> codeColumn;
    public TableColumn<DiscountCode, Double> percentColumn;
    public TableColumn<DiscountCode, String> startDateColumn;
    public TableColumn<DiscountCode, String> finalDateColumn;


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
        double minimum;
        if (!noMinimumCheckBox.isSelected()) {
            minimum = Double.parseDouble(minimumTotalPrice.getText());
        }
        else {
            minimum = -1;
        }
        double maximum = Double.parseDouble(maximumDiscountAmount.getText());
        String code = codeText.getText();
        ManagerBoss.createDiscountCode(code, finalFullDate, startFullDate, percentage, maximum, repeat, includedCustomers, minimum);
        setInformation("Successfully Created :)", false);
        updateScreen();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateScreen();
    }
    ObservableList<DiscountCode> observableList = FXCollections.observableArrayList();
    private void updateScreen() {
        observableList.clear();
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("Code"));
        percentColumn.setCellValueFactory(new PropertyValueFactory<>("DiscountPercent"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("StartDateSimpleString"));
        finalDateColumn.setCellValueFactory(new PropertyValueFactory<>("FinalDateSimpleString"));

        observableList.addAll(DiscountCode.getAllDiscountCodes());
        discountCodesTable.setItems(observableList);


        selectedCodeData.setText("Not Selected Item.");
    }
    public void clickDiscountCodesTable(MouseEvent mouseEvent) {
        if (discountCodesTable.getSelectionModel().getSelectedItem() != null) {
            selectedCodeData.setText(discountCodesTable.getSelectionModel().getSelectedItem().getDetails());
            fillOutInputs(discountCodesTable.getSelectionModel().getSelectedItem());
        }
        else {
            selectedCodeData.setText("Not Selected Item.");
        }
    }


    private void fillOutInputs(DiscountCode discountCode) {
        codeText.setText(discountCode.getCode());
        percent.setText(String.valueOf(discountCode.getDiscountPercent()));
        maximumDiscountAmount.setText(String.valueOf(discountCode.getMaximumAvailableAmount()));
        repeatRate.setText(String.valueOf(discountCode.getAvailableUseFrequent()));
        startTime.setText(discountCode.getStartDate().toString().substring(discountCode.getStartDate().toString().indexOf('T') + 1) + ":00");
        finalTime.setText(discountCode.getFinalDate().toString().substring(discountCode.getFinalDate().toString().indexOf('T') + 1) + ":00");


    }
    private void clearInputs() {
        codeText.setText("");
        percent.setText("");
        maximumDiscountAmount.setText("");
        repeatRate.setText("");
        startTime.setText("");
        finalTime.setText("");
    }


    public void removeDiscountCodeClick(MouseEvent mouseEvent) {
        if (discountCodesTable.getSelectionModel().getSelectedItem() != null) {
            try {
                ManagerBoss.deleteDiscountCodeWithCode(discountCodesTable.getSelectionModel().getSelectedItem().getCode());
                updateScreen();
            } catch (DiscountNotExist discountNotExist) {
                setInformation(discountNotExist.getMessage(), true);
            }
        }
    }

    public void editDiscountCodeClick(MouseEvent mouseEvent) {
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
        double minimum;
        if (!noMinimumCheckBox.isSelected()) {
            minimum = Double.parseDouble(minimumTotalPrice.getText());
        }
        else {
            minimum = -1;
        }
        double maximum = Double.parseDouble(maximumDiscountAmount.getText());
        String code = codeText.getText();
        ManagerBoss.editDiscountCode(discountCodesTable.getSelectionModel().getSelectedItem().getCode(), code, finalFullDate, startFullDate, percentage, maximum, repeat, includedCustomers, minimum);
        setInformation("Successfully Edited :)", false);
        updateScreen();

    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.doBack();
    }
}
