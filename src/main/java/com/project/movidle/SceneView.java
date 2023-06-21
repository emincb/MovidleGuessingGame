package com.project.movidle;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SceneView {
private TableView<AttributeItem> attributesTableView;

    public SceneView(TableView<AttributeItem> attributesTableView) {
    this.attributesTableView = attributesTableView;
    }

    public void GenerateTable(){
        TableColumn<AttributeItem, String> attributeColumn = new TableColumn<>("Attribute");
        attributeColumn.setCellValueFactory(cellData -> cellData.getValue().attributeProperty());

        TableColumn<AttributeItem, String> guess1Column = new TableColumn<>("Guess 1");
        guess1Column.setCellValueFactory(cellData -> cellData.getValue().guess1Property());

        TableColumn<AttributeItem, String> guess2Column = new TableColumn<>("Guess 2");
        guess2Column.setCellValueFactory(cellData -> cellData.getValue().guess2Property());

        TableColumn<AttributeItem, String> guess3Column = new TableColumn<>("Guess 3");
        guess3Column.setCellValueFactory(cellData -> cellData.getValue().guess3Property());

        TableColumn<AttributeItem, String> guess4Column = new TableColumn<>("Guess 4");
        guess4Column.setCellValueFactory(cellData -> cellData.getValue().guess4Property());

        TableColumn<AttributeItem, String> guess5Column = new TableColumn<>("Guess 5");
        guess5Column.setCellValueFactory(cellData -> cellData.getValue().guess5Property());

        TableColumn<AttributeItem, String> correctGuessColumn = new TableColumn<>("Correct Guess");
        correctGuessColumn.setCellValueFactory(cellData -> cellData.getValue().correctGuessProperty());


        attributesTableView.getColumns().addAll(attributeColumn, guess1Column, guess2Column, guess3Column, guess4Column, guess5Column, correctGuessColumn);

        AttributeItem titleItem = new AttributeItem("Title");
        AttributeItem yearItem = new AttributeItem("Year");
        AttributeItem genreItem = new AttributeItem("Genre");
        AttributeItem originItem = new AttributeItem("Origin");
        AttributeItem directorItem = new AttributeItem("Director");
        AttributeItem starItem = new AttributeItem("Star");

        attributesTableView.getItems().addAll(titleItem, yearItem, genreItem, originItem, directorItem, starItem);
    }
}
