package ViewControllers;

import Controller.SellerBoss;
import Model.Account;
import Model.Category;
import Model.SellLog;
import Model.Seller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    public CheckBox check;
    public Label problem;
    public Label specialAttributes;
    public TextField categoryName;
    public TableView table;
    public TableColumn nameOfSpecialAttributes;
    public TableColumn specialId;

    public void confirm(MouseEvent mouseEvent) {
        if (!check.isSelected()){
            problem.setText("you should check maybe this category has special attributes");
            problem.setTextFill(Paint.valueOf("red"));
            return;
        }
    }
    static class tableOfSpecials{
        private SimpleStringProperty fName;
        private SimpleStringProperty fValue;

        private String name;
        private String id;
        private static ArrayList<tableOfSpecials> allTablesOfSpecials = new ArrayList<>();
        public tableOfSpecials(String fName, String fValue) {
            this.fName = new SimpleStringProperty(fName);
            this.fValue = new SimpleStringProperty(fValue);
            name = fValue;
            id = fName;
            allTablesOfSpecials.add(this);
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getfName() {
            return fName.get();
        }

        public String getfValue() {
            return fValue.get();
        }

        public static ArrayList<tableOfSpecials> getAllTablesOfSpecials() {
            return allTablesOfSpecials;
        }
    }

    public void select(ActionEvent actionEvent) {

        ArrayList<String> a = new ArrayList<>();
        a.add("ads");
        a.add("asdd");
        Category category = new Category("asd",a);
        int i =0;
        for (String attribute :category.getSpecialAttributes()) {
            System.out.println(attribute);
            SimpleStringProperty simpleStringProperty = new SimpleStringProperty(attribute);
            SimpleStringProperty simpleStringProperty1 = new SimpleStringProperty(String.valueOf(i));
            tableOfSpecials tableOfSpecials = new tableOfSpecials(String.valueOf(i),attribute);
        }
        final ObservableList<tableOfSpecials> data = FXCollections.observableArrayList(tableOfSpecials.getAllTablesOfSpecials());
       nameOfSpecialAttributes.setCellValueFactory(new PropertyValueFactory<tableOfSpecials, String>("name"));

       specialId.setCellValueFactory(new PropertyValueFactory<tableOfSpecials,String>("id"));
        table.setItems(data);



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
