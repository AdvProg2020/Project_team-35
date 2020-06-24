package ViewControllers;

import Controller.Exceptions.NotValidRequestIdException;
import Controller.ManagerBoss;
import Main.Main;
import Model.Manager;
import Model.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RequestsPage implements Initializable {
    public TableView<Request> requestsTable;
    public TableColumn<Request, Integer> requestIdCol;
    public TableColumn<Request, String> requesterUsernameCol;
    public TableColumn<Request, String> typeOfRequestCol;
    public TableColumn<Request, String> situationCol;
    public TableView<Request> checkedTable;
    public TableColumn<Request, String> checkedRequestIdCol;
    public TableColumn<Request, String> checkedRequesterUsernameCol;
    public TableColumn<Request, String> checkedTypeOfRequestCol;
    public TableColumn<Request, String> checkedSituationCol;
    public Label actionInfo;
    public Label newRequestsInfo;
    public Label checkedRequestsInfo;

    private Request selectedRequest;
    private Request checkedSelectedRequest;

    ObservableList<Request> observableList = FXCollections.observableArrayList();
    ObservableList<Request> checkedObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        updateScreenTables();

    }

    public void newRequestsTableClick(MouseEvent mouseEvent) {
        selectedRequest = requestsTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            newRequestsInfo.setText(selectedRequest.getDetails());
            newRequestsInfo.setTextFill(Color.GREEN);
        }
    }

    public void checkedRequestsTableClick(MouseEvent mouseEvent) {
            checkedRequestsInfo.setText(checkedTable.getSelectionModel().getSelectedItem().getDetails());
            checkedRequestsInfo.setTextFill(Color.GREEN);
    }

    public void acceptRequest(MouseEvent mouseEvent) {
        if (selectedRequest != null) {
            try {
                ManagerBoss.acceptRequestWithId(selectedRequest.getRequestId());
                actionInfo.setText("Successful :)");
                actionInfo.setTextFill(Color.GREEN);
                selectedRequest = null;
                updateScreenTables();
            } catch (NotValidRequestIdException e) {
                newRequestsInfo.setText(e.getMessage());
                newRequestsInfo.setTextFill(Color.RED);
            }
        }
        else {
            actionInfo.setTextFill(Color.RED);
            actionInfo.setText("Request Not Selected.");
        }
    }

    public void declineRequest(MouseEvent mouseEvent) {
        if (selectedRequest != null) {
            try {
                ManagerBoss.declineRequestWithId(selectedRequest.getRequestId());
                newRequestsInfo.setText("Successful :)");
                newRequestsInfo.setTextFill(Color.GREEN);
                selectedRequest = null;
                updateScreenTables();
            } catch (NotValidRequestIdException e) {
                newRequestsInfo.setText(e.getMessage());
                newRequestsInfo.setTextFill(Color.RED);
            }
        }
        else {
            actionInfo.setTextFill(Color.RED);
            actionInfo.setText("Request Not Selected.");
        }
    }




    private void updateNewRequestsTableScreen() {
        requestIdCol.setCellValueFactory(new PropertyValueFactory<>("RequestId"));
        requesterUsernameCol.setCellValueFactory(new PropertyValueFactory<>("RequesterUsername"));
        typeOfRequestCol.setCellValueFactory(new PropertyValueFactory<>("RequestType"));
        situationCol.setCellValueFactory(new PropertyValueFactory<>("Situation"));
        observableList.addAll(Manager.getNewRequests());
        requestsTable.setItems(observableList);

    }

    private void updateCheckedRequestsTableScreen() {
        checkedRequestIdCol.setCellValueFactory(new PropertyValueFactory<>("RequestId"));
        checkedRequesterUsernameCol.setCellValueFactory(new PropertyValueFactory<>("RequesterUsername"));
        checkedTypeOfRequestCol.setCellValueFactory(new PropertyValueFactory<>("RequestType"));
        checkedSituationCol.setCellValueFactory(new PropertyValueFactory<>("Situation"));
        checkedObservableList.addAll(Manager.getCheckedRequests());
        checkedTable.setItems(checkedObservableList);
    }
    private void updateScreenTables() {
        newRequestsInfo.setText("");
        checkedRequestsInfo.setText("");
        observableList.clear();
        checkedObservableList.clear();
        updateCheckedRequestsTableScreen();
        updateNewRequestsTableScreen();
    }

    public void backClick(MouseEvent mouseEvent) throws IOException {
        Main.doBack();
    }
}
