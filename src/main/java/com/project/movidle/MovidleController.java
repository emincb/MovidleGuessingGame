package com.project.movidle;

import javafx.animation.FillTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MovidleController {
    @FXML
    private TextField guessTextField;
    @FXML
    private TableView<AttributeItem> attributesTableView;
    @FXML
    private Button guessButton;
    @FXML
    private Button restartButton;
    @FXML
    private Label messageLabel;

    private List<Movie> movies;
    private Movie currentMovie;
    private int remainingGuesses;

    public void initialize() {
        CSVReader csvReader = new CSVReader();
        movies = CSVReader.readMovies();

        startNewGame();
    }

    @FXML
    private void guessButtonClicked() {
        String guess = guessTextField.getText().trim();

        if (guess.isEmpty()) {
            return;
        }

        if (currentMovie == null) {
            startNewGame();
        }

        if (guess.equalsIgnoreCase(currentMovie.getTitle())) {
            showWinMessage();
        } else {
            remainingGuesses--;
            if (remainingGuesses == 0) {
                showLoseMessage();
            } else {
                showIncorrectGuess();
            }
        }

        guessTextField.clear();
    }

    @FXML
    private void restartButtonClicked() {
        startNewGame();
    }

    private void startNewGame() {
        Random random = new Random();
        int randomIndex = random.nextInt(movies.size());
        currentMovie = movies.get(randomIndex);
        remainingGuesses = 5;
        messageLabel.setText("");

        attributesTableView.getColumns().clear();
        attributesTableView.getItems().clear();

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


    private void showWinMessage() {
        messageLabel.setText("You Win!");
        messageLabel.setStyle("-fx-text-fill: green;");
        guessButton.setDisable(true);
    }

    private void showLoseMessage() {
        messageLabel.setText("You Lose! The movie was: " + currentMovie.getTitle());
        messageLabel.setStyle("-fx-text-fill: red;");
        guessButton.setDisable(true);

        // Get the guessed items
        List<AttributeItem> attributeItems = attributesTableView.getItems();

        // Set the guessed values and highlight the correct ones
        String guess = guessTextField.getText().trim();

        // Find the movie object that matches the guessed title
        Movie guessedMovie = null;
        for (Movie movie : movies) {
            if (guess.equalsIgnoreCase(movie.getTitle())) {
                guessedMovie = movie;
                break;
            }
        }

        // Update the attribute values and correctness for the guessed movie
        for (AttributeItem item : attributeItems) {
            String attributeName = item.getAttribute();
            String attributeValue = "";


            // Check if the attribute value of the guessed movie matches the attribute value of the correct movie
            if (guessedMovie != null) {
                attributeValue = guessedMovie.getAttributeValue(attributeName);
                attributeValue.equalsIgnoreCase(currentMovie.getAttributeValue(attributeName));
            }

            item.setValue(attributeValue);
            item.setGuess(attributeValue, remainingGuesses);
            item.setCorrectGuess(currentMovie.getAttributeValue(attributeName));

        }

        // Clear the guess text field
        guessTextField.clear();

        // Refresh the table view to update the cell colors
        attributesTableView.refresh();
    }


    private void showIncorrectGuess() {
        messageLabel.setText("Incorrect guess! Remaining guesses: " + remainingGuesses);
        messageLabel.setStyle("-fx-text-fill: red;");

        // Get the guessed items
        List<AttributeItem> attributeItems = attributesTableView.getItems();

        // Set the guessed values and highlight the correct ones
        String guess = guessTextField.getText().trim();

        // Find the movie object that matches the guessed title
        Movie guessedMovie = null;
        for (Movie movie : movies) {
            if (guess.equalsIgnoreCase(movie.getTitle())) {
                guessedMovie = movie;
                break;
            }
        }

        // Update the attribute values and highlight the guessed movie cell
        for (AttributeItem item : attributeItems) {
            String attributeName = item.getAttribute();
            String attributeValue = "";

            // Check if the attribute value of the guessed movie matches the attribute value of the correct movie
            if (guessedMovie != null) {
                if (attributeName.equalsIgnoreCase("Title")) {
                    attributeValue = guessedMovie.getTitle();
                } else if (attributeName.equalsIgnoreCase("Year")) {
                    attributeValue = guessedMovie.getYear();
                } else if (attributeName.equalsIgnoreCase("Genre")) {
                    attributeValue = guessedMovie.getGenre();
                } else if (attributeName.equalsIgnoreCase("Origin")) {
                    attributeValue = guessedMovie.getOrigin();
                } else if (attributeName.equalsIgnoreCase("Director")) {
                    attributeValue = guessedMovie.getDirector();
                } else if (attributeName.equalsIgnoreCase("Star")) {
                    attributeValue = guessedMovie.getStar();
                }
            }

            item.setValue(attributeValue);
            item.setGuess(attributeValue, remainingGuesses);

            // Apply cell factory to highlight the cell of the guessed movie in green
            TableColumn<AttributeItem, String> attributeColumn = (TableColumn<AttributeItem, String>) attributesTableView.getColumns().get(0);
            String finalAttributeValue = attributeValue;
            attributeColumn.setCellFactory(column -> new TableCell<AttributeItem, String>() {
                @Override
                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty);
                    setText(empty ? "" : value);

                    if (!empty && finalAttributeValue.equalsIgnoreCase(getItem())) {
                        setStyle("-fx-text-fill: green;");
                    } else {
                        setStyle("");
                    }
                }
            });

            int guessIndex = 6 - remainingGuesses;
            if (guessIndex >= 1 && guessIndex <= 5) {
                TableColumn<AttributeItem, String> guessColumn = (TableColumn<AttributeItem, String>) attributesTableView.getColumns().get(guessIndex);
                guessColumn.setCellFactory(column -> new TableCell<AttributeItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        setText(empty ? "" : value);
                    }
                });
            }
        }

        // Clear the guess text field
        guessTextField.clear();

        // Refresh the text fields to update their appearance
        attributesTableView.refresh();
    }


}
