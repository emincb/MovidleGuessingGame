package com.project.movidle.View;

import com.project.movidle.Movie;
import javafx.scene.control.Label;


public class LabelView {

        private Label messageLabel;
        public LabelView(Label messageLabel) {
            this.messageLabel = messageLabel;
        }
        public void handleWin(){
            messageLabel.setText("You Win!");
            messageLabel.setStyle("-fx-text-fill: green;");
        }
        public void handleLose(Movie currentMovie){
            messageLabel.setText("You Lose! The movie was: " + currentMovie.getTitle());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
        public void handleIncorrectGuess(int remainingGuesses){
            messageLabel.setText("Incorrect guess! Remaining guesses: " + remainingGuesses);
            messageLabel.setStyle("-fx-text-fill: #ff0000;");
        }

        public void handleNullMovie(String guess,int remainingGuesses){
            var msg = "No movie found with '" + guess +"' check your typo. Remaining guesses " + remainingGuesses;
            messageLabel.setText(msg);
            messageLabel.setStyle("-fx-text-fill: red");
        }
}
