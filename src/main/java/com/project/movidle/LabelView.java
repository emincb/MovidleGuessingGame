package com.project.movidle;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;

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
}
