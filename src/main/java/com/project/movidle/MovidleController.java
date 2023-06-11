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
            showMovieItems();
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
        showMovieItems();
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

        if (guessedMovie != null) {
            // Update the attribute values and correctness
            for (AttributeItem item : attributeItems) {
                String attributeName = item.getAttribute();
                String attributeValue = guessedMovie.getAttributeValue(attributeName);
                item.setValue(attributeValue);
                item.setGuess(attributeValue, remainingGuesses);
            }
        } else {
            // Display the attribute values of currentMovie in the "Correct Guess" column
            for (AttributeItem item : attributeItems) {
                String attributeName = item.getAttribute();
                String attributeValue = currentMovie.getAttributeValue(attributeName);
                item.setValue(attributeValue);
                item.setCorrectGuess(attributeValue.equals(item.getGuess5()) ? attributeValue : "");
            }
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

        // Update the attribute values and correctness for the guessed movie
        boolean hasCorrectGuess = false;
        for (AttributeItem item : attributeItems) {
            String attributeName = item.getAttribute();
            String attributeValue = "";
            boolean correct = false;

            // Check if the attribute value of the guessed movie matches the attribute value of the correct movie
            if (guessedMovie != null) {
                if (attributeName.equalsIgnoreCase("Title")) {
                    attributeValue = guessedMovie.getTitle();
                    correct = guess.equalsIgnoreCase(currentMovie.getTitle());
                } else if (attributeName.equalsIgnoreCase("Year")) {
                    attributeValue = guessedMovie.getYear();
                    correct = guess.equalsIgnoreCase(currentMovie.getYear());
                } else if (attributeName.equalsIgnoreCase("Genre")) {
                    attributeValue = guessedMovie.getGenre();
                    correct = guess.equalsIgnoreCase(currentMovie.getGenre());
                } else if (attributeName.equalsIgnoreCase("Origin")) {
                    attributeValue = guessedMovie.getOrigin();
                    correct = guess.equalsIgnoreCase(currentMovie.getOrigin());
                } else if (attributeName.equalsIgnoreCase("Director")) {
                    attributeValue = guessedMovie.getDirector();
                    correct = guess.equalsIgnoreCase(currentMovie.getDirector());
                } else if (attributeName.equalsIgnoreCase("Star")) {
                    attributeValue = guessedMovie.getStar();
                    correct = guess.equalsIgnoreCase(currentMovie.getStar());
                }
            }

            item.setValue(attributeValue);
            item.setGuess(attributeValue, remainingGuesses);
            item.setCorrectGuess(correct ? "Correct" : "");

            if (correct) {
                hasCorrectGuess = true;
            }
        }

        // If all guesses are used and there was no correct guess, display the correct attribute values
        if (remainingGuesses == 0 && !hasCorrectGuess) {
            for (AttributeItem item : attributeItems) {
                String attributeName = item.getAttribute();
                String attributeValue = currentMovie.getAttributeValue(attributeName);
                item.setValue(attributeValue);
                item.setCorrectGuess("");
            }
        }

        // Update the attribute values and correctness for the 5th guessed movie
        if (remainingGuesses == 1) {
            AttributeItem fifthGuessItem = attributeItems.get(5);
            String fifthGuessAttribute = fifthGuessItem.getAttribute();
            String fifthGuessValue = currentMovie.getAttributeValue(fifthGuessAttribute);
            fifthGuessItem.setValue(fifthGuessValue);
            fifthGuessItem.setGuess(fifthGuessValue, remainingGuesses);
            fifthGuessItem.setCorrectGuess("");
        }

        // Update the attribute values and correctness for the correct guess movie
        if (guessedMovie != null && guess.equalsIgnoreCase(currentMovie.getTitle())) {
            for (AttributeItem item : attributeItems) {
                String attributeName = item.getAttribute();
                String attributeValue = currentMovie.getAttributeValue(attributeName);
                item.setValue(attributeValue);
                item.setCorrectGuess("Correct");
            }
        }

        // Clear the guess text field
        guessTextField.clear();

        // Refresh the table view to update the cell colors
        attributesTableView.refresh();
    }


    private void showMovieItems() {
        if (currentMovie == null) {
            return;
        }

        List<AttributeItem> attributeItems = attributesTableView.getItems();

        attributeItems.get(0).setValue(currentMovie.getTitle());
        attributeItems.get(1).setValue(currentMovie.getYear());
        attributeItems.get(2).setValue(currentMovie.getGenre());
        attributeItems.get(3).setValue(currentMovie.getOrigin());
        attributeItems.get(4).setValue(currentMovie.getDirector());
        attributeItems.get(5).setValue(currentMovie.getStar());

        // Clear the guess columns and set the guesses from the CSV file
        for (AttributeItem item : attributeItems) {
            item.clearGuesses();
            String attribute = item.getAttribute();

            // Find the movie object that matches the current movie's title
            Movie matchingMovie = null;
            for (Movie movie : movies) {
                if (currentMovie.getTitle().equalsIgnoreCase(movie.getTitle())) {
                    matchingMovie = movie;
                    break;
                }
            }

            // Set the guess values from the matching movie
            if (matchingMovie != null) {
                item.setGuess(matchingMovie.getAttributeValue(attribute), remainingGuesses);
            }
        }
    }

}
