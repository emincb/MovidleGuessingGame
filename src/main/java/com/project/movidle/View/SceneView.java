package com.project.movidle.View;


import com.project.movidle.AttributeItem;
import com.project.movidle.Helpers.Helpers;
import com.project.movidle.Movie;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class SceneView {
    private TableView<AttributeItem> attributesTableView;

    public SceneView(TableView<AttributeItem> attributesTableView) {
        this.attributesTableView = attributesTableView;
    }


    public void GenerateTable() {
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

    public void GuessColumnColorHandler(int guessIndex, Movie currentMovie) {
        TableColumn<AttributeItem, String> guessColumn = (TableColumn<AttributeItem, String>) attributesTableView.getColumns().get(guessIndex);
        guessColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) setText("");

                if (!empty && Helpers.isNumber(value)) {
                    int year = Integer.parseInt(value);
                    int currentMovieYear = Integer.parseInt(currentMovie.getAttributeValue("Year"));
                    setText(value + (year < currentMovieYear ? "⬆" : "⬇" ));
                } else {
                    setText(value);
                }
                if (currentMovie.Includes(value)) {
                    setStyle("-fx-background-color: #00FF7F");
                } else {
                    setStyle("-fx-background-color: #FF6347");
                }
            }
        });
    }

    public void AddMovie(Movie movie) {
        List<AttributeItem> attributeItems = attributesTableView.getItems();

        // Update the attribute values and correctness for the guessed movie
        for (AttributeItem item : attributeItems) {
            String attributeName = item.getAttribute();
            String attributeValue = movie.getAttributeValue(attributeName);

            item.setValue(attributeValue);
            item.setCorrectGuess(movie.getAttributeValue(attributeName));


        }
    }

    public void HighlightCorrectMovie() {
        TableColumn<AttributeItem, String> guessColumn = (TableColumn<AttributeItem, String>) attributesTableView.getColumns().get(6);
        guessColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty ? "" : value);
                setStyle("-fx-background-color: #3cde3c");
            }
        });
    }

    public void HighlightCells(Movie guessedMovie, Movie currentMovie, int remainingGuesses) {
        List<AttributeItem> attributeItems = attributesTableView.getItems();
        for (AttributeItem item : attributeItems) {
            String attributeName = item.getAttribute();
            String attributeValue = guessedMovie.getAttributeValue(attributeName);
            item.setValue(attributeValue);
            item.setGuess(attributeValue, remainingGuesses);


            int guessIndex = 5 - remainingGuesses;
            if (guessIndex >= 1 && guessIndex <= 5) {
                GuessColumnColorHandler(guessIndex, currentMovie);
            }

        }
    }
}
