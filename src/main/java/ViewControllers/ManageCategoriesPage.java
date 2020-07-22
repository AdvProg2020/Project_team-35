package ViewControllers;

import Controller.Exceptions.FieldDoesNotExist;
import Controller.Exceptions.RepeatedCategoryAttributeException;
import Controller.Exceptions.RepeatedCategoryNameException;
import Controller.Exceptions.ThereIsNotCategoryWithNameException;
import Controller.ManagerBoss;
import Main.Main;
import Model.Account;
import Model.Category;
import MusicPlayer.MusicPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.List;

public class ManageCategoriesPage implements Initializable {
    public TableView<Category> categoriesTable;
    public TableColumn<Category, String> categoryNameCol;
    public Label categoryInfo;
    public TextField categoryAddDeleteName;
    public TextArea addCategoryAttributes;
    public TextField categoryNewNameForRename;
    public TextField attributeNameForAddOrDeleteAttribute;
    public TextField selectedCategoryName;
    public TextField attributeNameForRename;
    public TextField attributeNewNameForRename;
    public Label actionInfo;
    private Category selectedCategory;



    private void freeTextFields() {

        attributeNewNameForRename.setText("");
        attributeNameForRename.setText("");
        selectedCategoryName.setText("");
        attributeNameForAddOrDeleteAttribute.setText("");
        categoryNewNameForRename.setText("");
        addCategoryAttributes.setText("");
        categoryAddDeleteName.setText("");
        categoryInfo.setText("");
    }

    public void clickCategoriesTable(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        selectedCategory = categoriesTable.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            categoryInfo.setText(selectedCategory.getSpecialAttributes().toString());
            selectedCategoryName.setText(selectedCategory.getCategoryName());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateScreen();
    }
    ObservableList<Category> observableList = FXCollections.observableArrayList();
    private void updateScreen() {
        observableList.clear();
        categoryNameCol.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        observableList.addAll(Category.getAllCategories());
        categoriesTable.setItems(observableList);


        selectedCategory = null;
        categoryInfo.setText("");
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        Main.doBack();
    }

    public void addCategoryClick(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        ArrayList<String> attributes = new ArrayList<>();
        Collections.addAll(attributes, addCategoryAttributes.getText().split("\\n"));
        Main.sendMessageToServer("MCategoryCreate" + categoryAddDeleteName.getText());
        Main.sendObjectToServer(attributes);
        String response = Main.getMessageFromServer();
        setActionInfo(response, false);
        freeTextFields();
//        try {
//            ManagerBoss.addNewCategory(categoryAddDeleteName.getText(), attributes);
//            setActionInfo("Category Successfully Added :)", false);
//            updateScreen();
//            freeTextFields();
//        } catch (RepeatedCategoryNameException e) {
//            setActionInfo(e.getMessage(), true);
//        }
    }

    public void deleteCategoryClick(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        if (selectedCategory == null) {
            setActionInfo("Not Selected", true);
            return;
        }
        try {
            ManagerBoss.startDeleteCategoryWithName(selectedCategory.getCategoryName());
            setActionInfo("Category Successfully Removed :)", false);
            updateScreen();
            freeTextFields();
        } catch (ThereIsNotCategoryWithNameException e) {
            setActionInfo(e.getMessage(), true);
        }
    }

    public void renameCategoryClick(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        try {
            ManagerBoss.checkCategoryExistence(selectedCategoryName.getText());
            ManagerBoss.editCategoryName(selectedCategoryName.getText(), categoryNewNameForRename.getText());
            setActionInfo("Category Successfully Renamed :)", false);
            updateScreen();
            freeTextFields();
        } catch (ThereIsNotCategoryWithNameException e) {
            setActionInfo(e.getMessage(), true);
        }
    }

    public void addAttributeToCategoryClick(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        try {
            ManagerBoss.checkCategoryExistence(selectedCategoryName.getText());
            ManagerBoss.addAttributeToCategory(selectedCategoryName.getText(), attributeNameForAddOrDeleteAttribute.getText());
            setActionInfo("Attribute Successfully Added To Category :)", false);
            updateScreen();
            freeTextFields();
        } catch (Exception e) {
            setActionInfo(e.getMessage(), true);
        }
    }

    public void deleteAttributeFromCategoryClick(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        if (categoriesTable.getSelectionModel().getSelectedItem() == null) {
            setActionInfo("Not Selected.", true);
            return;
        }
        try {
            ManagerBoss.deleteAttributeFromCategory(categoriesTable.getSelectionModel().getSelectedItem().getCategoryName(), attributeNameForAddOrDeleteAttribute.getText());
            setActionInfo("Attribute Successfully Deleted :)", false);
            updateScreen();
            freeTextFields();
        } catch (FieldDoesNotExist fieldDoesNotExist) {
            setActionInfo(fieldDoesNotExist.getMessage(), true);
        }
    }

    public void renameAttributeClick(MouseEvent mouseEvent) {
        MusicPlayer.getInstance().playButtonMusic();
        if (categoriesTable.getSelectionModel().getSelectedItem() == null) {
            setActionInfo("Not Selected.", true);
            return;
        }
        try {
            ManagerBoss.editAttributeName(categoriesTable.getSelectionModel().getSelectedItem().getCategoryName(), attributeNameForRename.getText(), attributeNewNameForRename.getText());
            setActionInfo("Attribute Successfully Renamed :)", false);
            updateScreen();
            freeTextFields();
        } catch (FieldDoesNotExist | RepeatedCategoryAttributeException e) {
            setActionInfo(e.getMessage(), true);
        }
    }


    private void setActionInfo(String message, boolean isError) {
        actionInfo.setText(message);
        if (isError) {
            actionInfo.setTextFill(Color.RED);
        }
        else {
            actionInfo.setTextFill(Color.GREEN);
        }
    }
}
