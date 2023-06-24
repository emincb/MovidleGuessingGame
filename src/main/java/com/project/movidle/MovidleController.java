package com.project.movidle;

import com.project.movidle.View.LabelView;
import com.project.movidle.View.SceneView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private Label messageLabel;
    private SceneView sceneView;
    private LabelView labelView;
    private List<Movie> movies;
    private Movie currentMovie;
    private int remainingGuesses;

    public void initialize() {
        CSVReader csvReader = new CSVReader();
        movies = CSVReader.readMovies();
        sceneView = new SceneView(attributesTableView);
        labelView = new LabelView(messageLabel);
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

        sceneView.GenerateTable();
        guessTextField.clear();
    }

    @FXML
    private void guessButtonClicked() {
        String guess = guessTextField.getText().trim();

        if (guess.isEmpty()) {
            return;
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

    private void showWinMessage() {
        labelView.handleWin();
        guessButton.setDisable(true);
    }

    private void showLoseMessage() {
        labelView.handleLose(currentMovie);
        guessButton.setDisable(true);

        var guessedMovie = findGuessedMovie();

        // Get the guessed items
        List<AttributeItem> attributeItems = attributesTableView.getItems();

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
        highlightCells(guessedMovie);
        sceneView.GuessColumnColorHandler(6, currentMovie);


        // Clear the guess text field
        guessTextField.clear();

        // Refresh the table view to update the cell colors
        attributesTableView.refresh();
    }

    private void showIncorrectGuess() {
        labelView.handleIncorrectGuess(remainingGuesses);

        var guessedMovie = findGuessedMovie();
        if (guessedMovie == null) {
            labelView.handleNullMovie(guessTextField.getText().trim(), remainingGuesses);
        } else {
            highlightCells(guessedMovie);
        }
        guessTextField.clear();
        // Refresh the text fields to update their appearance
        attributesTableView.refresh();
    }

    @FXML
    private void restartButtonClicked() {
        startNewGame();
        guessButton.setDisable(false);
    }

    private void highlightCells(Movie guessedMovie){
        // Update the attribute values and highlight the guessed movie cell
        List<AttributeItem> attributeItems = attributesTableView.getItems();
        for (AttributeItem item : attributeItems) {
            String attributeName = item.getAttribute();
            String attributeValue = guessedMovie.getAttributeValue(attributeName);
            item.setValue(attributeValue);
            item.setGuess(attributeValue, remainingGuesses);


            int guessIndex = 5 - remainingGuesses;
            if (guessIndex >= 1 && guessIndex <= 5) {
                sceneView.GuessColumnColorHandler(guessIndex, currentMovie);
            }

        }
    }

    private Movie findGuessedMovie() {
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

        return guessedMovie;
    }
}
