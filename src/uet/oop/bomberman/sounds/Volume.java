package uet.oop.bomberman.sounds;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uet.oop.bomberman.BombermanGame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Volume {
    public static final SoundEffect titleScreen = new SoundEffect();
    public static final SoundEffect stageStart = new SoundEffect();
    public static final SoundEffect menuChoosing = new SoundEffect();
    public static final SoundEffect stageTheme = new SoundEffect();
    public static final SoundEffect justDied = new SoundEffect();
    public static final SoundEffect bombPlaced = new SoundEffect();
    public static final SoundEffect powerUp = new SoundEffect();
    public static final SoundEffect gameOver = new SoundEffect();

    public static final SoundEffect ending = new SoundEffect();
    public static final SoundEffect stageClear = new SoundEffect();
    public static final SoundEffect bombExplosion = new SoundEffect();
    private static final List<SoundEffect> soundList = new ArrayList<>();
    public VBox volumeChange() {
        try{
            titleScreen.setFile("res/soundfx/titleScreen.wav");
            stageStart.setFile("res/soundfx/stageStart.wav");
            menuChoosing.setFile("res/soundfx/menuChoosing.wav");
            stageTheme.setFile("res/soundfx/stageTheme.wav");
            bombPlaced.setFile("res/soundfx/bombSet.wav");
            bombExplosion.setFile("res/soundfx/bombExplosion.wav");
            justDied.setFile("res/soundfx/justDied.wav");
            powerUp.setFile("res/soundfx/Power-up.wav");
            gameOver.setFile("res/soundfx/gameOver.wav");
            ending.setFile("res/soundfx/Ending.wav");
            stageClear.setFile("res/soundfx/stageClear.wav");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }
        soundList.add(titleScreen);
        soundList.add(stageTheme);
        soundList.add(stageStart);
        soundList.add(menuChoosing);
        soundList.add(bombPlaced);
        soundList.add(justDied);
        soundList.add(powerUp);
        soundList.add(gameOver);
        soundList.add(ending);
        soundList.add(stageClear);
        soundList.add(bombExplosion);
        VBox box = new VBox(20);

        Button mute = new Button("Mute");
        try {
            mute.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 15));
        } catch (FileNotFoundException e) {
            mute.setFont(Font.font("Bungee Inline", 15));
        }

        Slider slider = new Slider();
        slider.setMin(-94.0);
        slider.setMax(6.0);
        slider.setValue(6.0);
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);

        Label infoLabel = new Label("Value: 100");
        try {
            infoLabel.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 15));
        } catch (FileNotFoundException e) {
            infoLabel.setFont(new Font("Bungee Inline", 15));
        }
        infoLabel.setTextFill(Color.BLACK);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            infoLabel.setText("Value: " + (newValue.intValue() + 94));
            for (SoundEffect soundEffect : soundList) {
                soundEffect.currentVolume = (float) slider.getValue();
                if (soundEffect.currentVolume < -80) {
                    soundEffect.currentVolume = -80;
                }
                soundEffect.getFc().setValue(soundEffect.currentVolume);
            }
        });

        mute.setOnAction(event -> {
            for (SoundEffect soundEffect : soundList) {
                soundEffect.mute();
            }
        });

        box.getChildren().addAll(infoLabel, slider, mute);
        return box;
    }
}
