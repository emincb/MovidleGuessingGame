package com.project.movidle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MovidleApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/project/movidle/movidle.fxml"));
            VBox root = loader.load();

            // Get the controller instance
            MovidleController movidleController = loader.getController();

            // Set the stage and scene
            Scene scene = new Scene(root);
            String cssFile = getClass().getResource("/com/project/movidle/styles.css").toExternalForm();
            scene.getStylesheets().add(cssFile);

            primaryStage.setTitle("Movidle Movie Guessing Game");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
