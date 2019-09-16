package com.celine.controller;

import com.celine.entity.Category;
import com.celine.entity.Item;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Tampilan implements Initializable {
    public TextField txtName;
    public TextField txtPrice;
    public ComboBox<Category> txtCategory;
    public TextField txtCategoryName;
    public TableColumn<Item, String> colName;
    public TableColumn<Item,String> colPrice;
    public TableColumn<Item, String> colCategory;
    public TableView<Item> tabelData;
    public Button btnSave;
    public Button btnReset;
    public Button btnUpdate;
    private ObservableList<Category> categories;
    private ObservableList<Item> items;
    private Item z;
    Alert error = new Alert(Alert.AlertType.ERROR);


    public void actionReset(ActionEvent actionEvent) {
        txtCategory.setValue(null);
        txtPrice.clear();
        txtName.clear();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
    }

    public void actionSave(ActionEvent actionEvent) {
        Item k = new Item();
        Boolean found = false;
        if(!txtName.getText().isEmpty() && !txtPrice.getText().isEmpty() && txtCategory.getValue()!=null){
            k.setName(txtName.getText());
            k.setPrice(Double.valueOf(txtPrice.getText()));
            k.setCategory(txtCategory.getValue());

            for (Item i:items){
                if(i.getName().equals(k.getName())){
                    found = true;
                    error.setContentText("Name of Item is duplicate");
                    error.showAndWait();
                    break;
                }
            }
            if (!found) {
                items.add(k);
            }
            txtName.clear();
            txtPrice.clear();
            txtCategory.setValue(null);
        }
        else{
            if(txtName.getText().isEmpty() && txtPrice.getText().isEmpty() && txtCategory.getValue()==null){
                error.setContentText("Please fill name/price/category");
            }
            error.show();
        }
    }

    public void actionUpdate(ActionEvent actionEvent) {
        int count =0 ;
        String tempName = z.getName();
        z.setName(txtName.getText());
        for (Item i:items){
            if(i.getName().equals(txtName.getText())){
                count+=1;
            }
        }
        if(count > 1){
            error.setContentText("Name of Item is duplicate");
            error.showAndWait();
            z.setName(tempName);
        }
        else{
            z.setPrice(Double.valueOf(txtPrice.getText()));
            z.setCategory(txtCategory.getValue());
        }
        tabelData.refresh();
    }

    public void actionSaveCategory(ActionEvent actionEvent) {
        Category c = new Category();
        boolean found = false;
        c.setName(txtCategoryName.getText());
        if(txtCategoryName.getText().isEmpty()){
                error.setContentText("Please fill category");
                error.show();
            }
        else{
            for (Category i:categories){
                if(i.getName().equals(c.getName())){
                    found = true;
                    error.setContentText("Category is duplicate");
                    error.showAndWait();
                    break;
                }
            }
            if (!found) {
                categories.add(c);
            }
        }
        txtCategoryName.clear();
    }

    public void tableClicked(MouseEvent mouseEvent) {
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        z = tabelData.getSelectionModel().getSelectedItem();
        txtName.setText(z.getName());
        txtPrice.setText(String.valueOf(z.getPrice()));
        txtCategory.setValue(z.getCategory());
    }

    public void actionAbout(ActionEvent actionEvent) {
    }

    public void actionClose(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categories = FXCollections.observableArrayList();
        items = FXCollections.observableArrayList();
        txtCategory.setItems(categories);
        tabelData.setItems(items);
        colName.setCellValueFactory(data->{
            Item j = data.getValue();

            return new SimpleStringProperty(j.getName());
        });
        colPrice.setCellValueFactory(data->{
            Item j = data.getValue();

            return  new SimpleStringProperty(String.valueOf(j.getPrice()));
        });
        colCategory.setCellValueFactory(data->{
            Item j = data.getValue();

            return new SimpleStringProperty(String.valueOf(j.getCategory()));
        });
    }
}
