package ViewControllers;

import Main.Main;
import Model.DiscountCode;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowDiscountCodesPage implements Initializable {
    public TableView discountsCodesTable;
    public TableColumn idCol;
    public TableColumn dateCol;
    public TableColumn percentCol;
    public TableColumn maxCol;
    public TableColumn freqCol;

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("MainMenu", "main menu", true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
    }

    public void update() {
        ObservableList<DiscountCode> observableList = FXCollections.observableArrayList(DiscountCode.getAllDiscountCodes());
        observableList.clear();
        idCol.setCallValueFactory(new PropertyValueFactory<>("code"));
        dateCol.setCallValueFactory(new PropertyValueFactory<>("finalDate"));
        percentCol.setCallValueFatory(new PropertyValueFactory<>("discountPercent"));


    }
}
