package com.project.movidle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.project.movidle.AutoCompleteTextField;
import java.util.*;


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
    private SceneView sceneView;
    private List<Movie> movies;
    private Movie currentMovie;
    private int remainingGuesses;

    public void initialize() {
        CSVReader csvReader = new CSVReader();
        movies = csvReader.readMovies();
        sceneView = new SceneView(attributesTableView);

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
        SceneView sceneView = new SceneView(attributesTableView);
        sceneView.GenerateTable();


        guessTextField.clear();


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
        // Update the attribute values and highlight the guessed movie cell
        for (AttributeItem item : attributeItems) {
            String attributeName = item.getAttribute();
            String attributeValue = guessedMovie.getAttributeValue(attributeName);

            item.setValue(attributeValue);
            item.setGuess(attributeValue, remainingGuesses);

            int guessIndex = 5 - remainingGuesses;

            sceneView.GuessColumnColorHandler(guessIndex, currentMovie);
        }
        sceneView.GuessColumnColorHandler(6, currentMovie);




        // Clear the guess text field
        guessTextField.clear();

        // Refresh the table view to update the cell colors
        attributesTableView.refresh();
    }


    private void showIncorrectGuess() {
        messageLabel.setText("Incorrect guess! Remaining guesses: " + remainingGuesses);
        messageLabel.setStyle("-fx-text-fill: #ff0000;");

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
            String attributeValue = guessedMovie.getAttributeValue(attributeName);
             Movie finalGuessedMovie = guessedMovie;
            item.setValue(attributeValue);
            item.setGuess(attributeValue, remainingGuesses);


            int guessIndex = 5 - remainingGuesses;
            if (guessIndex >= 1 && guessIndex <= 5) {
                TableColumn<AttributeItem, String> guessColumn = (TableColumn<AttributeItem, String>) attributesTableView.getColumns().get(guessIndex);
                guessColumn.setCellFactory(column -> new TableCell<AttributeItem, String>() {
                    @Override
                    protected void updateItem(String value, boolean empty) {
                        super.updateItem(value, empty);
                        setText(empty ? "" : value);
                        if (currentMovie.Includes(value)) {
                            setStyle("-fx-background-color: #3cde3c");
                        } else {
                            setStyle("-fx-background-color: #ff0000");

                        }
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
