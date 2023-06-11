package com.project.movidle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovidleController {
    @FXML
    private TilePane tilePane;
    @FXML
    private TextField guessTextField;
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
        loadMovies();
        startNewGame();
    }

    @FXML
    private void handleGuessButtonAction() {
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

    @FXML
    private void handleRestartButtonAction() {
        startNewGame();
    }

    public void cleanup() {
        // Perform any necessary cleanup tasks here
    }

    private void loadMovies() {
        CSVReader csvReader = new CSVReader();
        movies = csvReader.readMovies();
        System.out.println(movies);
    }

    private void startNewGame() {
        Random random = new Random();
        int randomIndex = random.nextInt(movies.size());
        currentMovie = movies.get(randomIndex);
          tilePane.getChildren().clear();
        remainingGuesses = 5;
        messageLabel.setText("");
        for (int i = 0; i < currentMovie.getTitle().length(); i++) {
            Label tile = new Label();
            tile.getStyleClass().add("tile");
            tilePane.getChildren().add(tile);
        }
    }

    private void showWinMessage() {
        messageLabel.setText("You Win!");
        messageLabel.setTextFill(Color.GREEN);
        guessButton.setDisable(true);
    }

    private void showLoseMessage() {
        messageLabel.setText("You Lose! The movie was: " + currentMovie.getTitle());
        messageLabel.setTextFill(Color.RED);
        guessButton.setDisable(true);
    }

    private void showIncorrectGuess() {
        messageLabel.setText("Incorrect guess! Remaining guesses: " + remainingGuesses);
        messageLabel.setTextFill(Color.RED);
        highlightIncorrectTiles();
    }

    private void highlightIncorrectTiles() {
        String guess = guessTextField.getText().trim();
        for (int i = 0; i < guess.length(); i++) {
            Label tile = (Label) tilePane.getChildren().get(i);
            if (!guess.regionMatches(true, i, currentMovie.getTitle(), i, 1)) {
                tile.getStyleClass().add("incorrect-tile");
            }
        }
    }
}