package com.project.movidle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class AttributeItem {
    private final SimpleStringProperty attribute;
    private final SimpleStringProperty value;
    private final SimpleStringProperty guess1;
    private final SimpleStringProperty guess2;
    private final SimpleStringProperty guess3;
    private final SimpleStringProperty guess4;
    private final SimpleStringProperty guess5;
    private final SimpleStringProperty correctGuess;

    public AttributeItem(String attribute) {
        this.attribute = new SimpleStringProperty(attribute);
        this.value = new SimpleStringProperty();
        this.guess1 = new SimpleStringProperty("");
        this.guess2 = new SimpleStringProperty("");
        this.guess3 = new SimpleStringProperty("");
        this.guess4 = new SimpleStringProperty("");
        this.guess5 = new SimpleStringProperty("");
        this.correctGuess = new SimpleStringProperty("");
    }

    public String getAttribute() {
        return attribute.get();
    }

    public SimpleStringProperty attributeProperty() {
        return attribute;
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public SimpleStringProperty guess1Property() {
        return guess1;
    }

    public SimpleStringProperty guess2Property() {
        return guess2;
    }

    public SimpleStringProperty guess3Property() {
        return guess3;
    }

    public SimpleStringProperty guess4Property() {
        return guess4;
    }

    public String getGuess5() {
        return guess5.get();
    }

    public SimpleStringProperty guess5Property() {
        return guess5;
    }

    public SimpleStringProperty correctGuessProperty() {
        return correctGuess;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public void setGuess(String guess, int remainingGuesses) {
        switch (remainingGuesses) {
            case 4:
                guess1.set(guess);
                break;
            case 3:
                guess2.set(guess);
                break;
            case 2:
                guess3.set(guess);
                break;
            case 1:
                guess4.set(guess);
                break;
            case 0:
                guess5.set(guess);
                break;
        }
    }

    public void setCorrectGuess(String correctGuess) {
        this.correctGuess.set(correctGuess);
    }

    public void clearGuesses() {
        guess1.set("");
        guess2.set("");
        guess3.set("");
        guess4.set("");
        guess5.set("");
        correctGuess.set("");
    }

}
