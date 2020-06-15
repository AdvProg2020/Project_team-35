package ViewControllers;


import Controller.Exceptions.CantRemoveYourAccountException;
import Controller.Exceptions.NotValidUserNameException;
import Controller.ManagerBoss;
import Main.Main;
import Model.Account;
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

public class UsersManagingPage implements Initializable {


    public TableView<Account> usersTable;
    public Label userInfo;
    public Label actionInfo;
    public TableColumn<Account, String> usernameCol;
    public TableColumn<Account, String> passwordCol;
    public TableColumn<Account, String> typeCol;

    private Account selectedAccount;

    public void clickUsersTable(MouseEvent mouseEvent) {
        userInfo.setText(usersTable.getSelectionModel().getSelectedItem().getPersonalInfo());
        selectedAccount = usersTable.getSelectionModel().getSelectedItem();
    }

    public void removeUserClick(MouseEvent mouseEvent) {
        if (selectedAccount != null) {
            try {
                ManagerBoss.deleteAccountWithUsername(selectedAccount.getUsername());
                actionInfo.setText("Successful :)");
                actionInfo.setTextFill(Color.GREEN);
                updateDataOnTheScreen();
            } catch (Exception e) {
                actionInfo.setTextFill(Color.RED);
                actionInfo.setText(e.getMessage());
            }
        }
        else {
            actionInfo.setTextFill(Color.RED);
            actionInfo.setText("Not Selected Account");
        }
    }

    ObservableList<Account> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateDataOnTheScreen();

    }

    private void updateDataOnTheScreen(){
        observableList.clear();
        userInfo.setText("");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("Password"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        observableList.addAll(Account.getAllAccounts());
        usersTable.setItems(observableList);

    }

    public void backClick(MouseEvent mouseEvent) throws IOException {
        Main.tree.pop();
        Main.setRoot(Main.tree.peek(), Main.tree.peek());
    }
}
