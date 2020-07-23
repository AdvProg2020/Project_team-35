package ViewControllers;

import Main.Main;
import Model.Request;
import Model.Supporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerSupportPage implements Initializable {

    public TableView<Supporter> onlineSupportersTable;
    public TableColumn<Supporter, String> supportersUserNames;
    public Label actionInfo;

    ObservableList<Supporter> observableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actionInfo.setText("");
        observableList.clear();
        supportersUserNames.setCellValueFactory(new PropertyValueFactory<>("Username"));
        try {
            Main.sendMessageToServer("MRequestsGetOnlineSupporters");
            ArrayList<Supporter> onlineSupporters = (ArrayList<Supporter>) Main.getObjectFromServer();
            observableList.addAll(onlineSupporters);
            onlineSupportersTable.setItems(observableList);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.doBack();
    }

    public void startChat(MouseEvent mouseEvent) throws IOException {
        Supporter selected = onlineSupportersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            actionInfo.setText("Not selected any supporter.");
        }
        else {
            Main.sendMessageToServer("MRequestsStartChatWith:" + selected.getUsername());
            String response = Main.getMessageFromServer();
            if (response.equalsIgnoreCase("Successful :)")) {
                Main.setRoot("CustomerChatPage", "Chat Page", false);
            }
            else {
                actionInfo.setText(response);
            }
        }
    }
}
