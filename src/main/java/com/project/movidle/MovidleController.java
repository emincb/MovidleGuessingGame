package com.project.movidle;

import com.project.movidle.Helpers.Autocomplete;
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
        movies = CSVReader.readMovies();
        Autocomplete ac = new Autocomplete(movies);
        guessTextField.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.length() >= 3 && oldVal.length() < newVal.length()) {
                var matches = ac.autocomplete(newVal);
                if(matches.isEmpty()) return;

                guessTextField.setText(matches.get(0));
            }
        });
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
            showIncorrectGuess();
        }

        guessTextField.clear();
        guessTextField.requestFocus();
    }

    private void showWinMessage() {
        labelView.handleWin();
        guessButton.setDisable(true);
    }

    private void showLoseMessage() {
        labelView.handleLose(currentMovie);
        guessButton.setDisable(true);

        sceneView.AddMovie(currentMovie);
        sceneView.HighlightCorrectMovie();

        guessTextField.clear();
        // Refresh the table view to update the cell colors
        attributesTableView.refresh();
    }

    private void showIncorrectGuess() {

        var guessedMovie = findGuessedMovie();
        if (guessedMovie == null) {
            labelView.handleNullMovie(guessTextField.getText().trim(), remainingGuesses);
        } else {
            sceneView.HighlightCells(guessedMovie, currentMovie, remainingGuesses);
            labelView.handleIncorrectGuess(remainingGuesses);
        }
        if (remainingGuesses == 0) showLoseMessage();
        guessTextField.clear();
        // Refresh the text fields to update their appearance
        attributesTableView.refresh();
    }

    @FXML
    private void restartButtonClicked() {
        startNewGame();
        guessButton.setDisable(false);
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
