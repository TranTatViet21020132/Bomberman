package uet.oop.bomberman.model;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.sounds.Volume;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SpaceRunnerButton extends Button {
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('resources/red_button.png')";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('resources/red_button_pressed.png')";
    private final static int buttonWidth = 190;
    private final static int buttonHeight = 49;

    public static int getButtonWidth() {
        return buttonWidth;
    }

    public static int getButtonHeight() {
        return buttonHeight;
    }
    public SpaceRunnerButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(buttonWidth);
        setPrefHeight(buttonHeight);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();
    }

    private void setButtonFont() {
        try{
            setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Bungee Inline", 20));
        }
    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void initializeButtonListeners() {
        setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
                Volume.menuChoosing.play();
            }
        });

        setOnMouseReleased(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleasedStyle();
            }
        });

        setOnMouseEntered(event -> {
            setEffect(new DropShadow());
        });

        setOnMouseExited(event -> {
            setEffect(null);
        });
    }
}
