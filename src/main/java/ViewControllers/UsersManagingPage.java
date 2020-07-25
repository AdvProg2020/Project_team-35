package ViewControllers;


import Controller.Exceptions.CantRemoveYourAccountException;
import Controller.Exceptions.NotValidUserNameException;
import Controller.ManagerBoss;
import Main.Main;
import Model.Account;
import Model.Request;
import MusicPlayer.MusicPlayer;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersManagingPage implements Initializable {


    public TableView<Account> usersTable;
    public Label userInfo;
    public Label actionInfo;
    public TableColumn<Account, String> usernameCol;
    public TableColumn<Account, String> passwordCol;
    public TableColumn<Account, String> typeCol;
    public Label isOnline;

    private ArrayList<Account> onlineAccounts;

    private Account selectedAccount;

    public void clickUsersTable(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        if(usersTable.getSelectionModel().getSelectedItem() != null) {
            userInfo.setText(usersTable.getSelectionModel().getSelectedItem().getPersonalInfo());
            selectedAccount = usersTable.getSelectionModel().getSelectedItem();
            if (checkOnline(selectedAccount)) {
                isOnline.setText("ONLINE :)");
            }
            else {
                isOnline.setText("OFFLINE :(");
            }
        }
        else {
            userInfo.setText("Not Selected.");
        }
    }

    public void removeUserClick(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        MusicPlayer.getInstance().playButtonMusic();
        if (selectedAccount != null) {
            Main.sendMessageToServer("MRequestsRemoveUser:" + selectedAccount.getUsername());
            String response = Main.getMessageFromServer();
            if (response.equalsIgnoreCase("successful")) {
                actionInfo.setText("Successful :)");
                actionInfo.setTextFill(Color.GREEN);
                updateDataOnTheScreen();
            }
            else {
                actionInfo.setText(response);
                actionInfo.setTextFill(Color.RED);
            }
//            try {
//                ManagerBoss.deleteAccountWithUsername(selectedAccount.getUsername());
//                actionInfo.setText("Successful :)");
//                actionInfo.setTextFill(Color.GREEN);
//                updateDataOnTheScreen();
//            } catch (Exception e) {
//                actionInfo.setTextFill(Color.RED);
//                actionInfo.setText(e.getMessage());
//            }
        }
        else {
            actionInfo.setTextFill(Color.RED);
            actionInfo.setText("Not Selected Account");
        }
    }

    ObservableList<Account> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateDataOnTheScreen();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void updateDataOnTheScreen() throws IOException, ClassNotFoundException {
        Main.sendMessageToServer("MRequestsGetAllAccounts");
        ArrayList<Account> allAccounts = (ArrayList<Account>) Main.getObjectFromServer();
        Main.sendMessageToServer("MRequestsGetOnlineAccounts");
        onlineAccounts = (ArrayList<Account>) Main.getObjectFromServer();
        selectedAccount = null;
        observableList.clear();
        userInfo.setText("");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("Password"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        observableList.addAll(allAccounts);
        usersTable.setItems(observableList);
    }

    public void backClick(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack();
    }

    private boolean checkOnline(Account account) {
        for (Account onlineAccount : onlineAccounts) {
            if (onlineAccount == null) {
                continue;
            }
            if (account.getUsername().equalsIgnoreCase(onlineAccount.getUsername())) {
                return true;
            }
        }
        return false;
    }
}
