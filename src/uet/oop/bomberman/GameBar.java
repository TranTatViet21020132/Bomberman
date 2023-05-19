package uet.oop.bomberman;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import uet.oop.bomberman.entities.mob.Bomber;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameBar {
    private final DropShadow ds = new DropShadow();
    private static final Integer STARTTIME = 360;
    private Timeline timeline;
    private Label timerLabel = new Label();
    private Label pointLabel = new Label();
    private Label livesLabel = new Label();
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

    public GridPane createGameBar() {
        ds.setColor(Color.BLACK);
        ds.setRadius(5);
        ds.setOffsetX(3);
        ds.setOffsetY(3);

        pointLabel.setText("Points: " + Bomber.score);
        pointLabel.setTextFill(Color.WHITESMOKE);
        pointLabel.setAlignment(Pos.CENTER_LEFT);
        pointLabel.setEffect(ds);

        timerLabel.textProperty().bind(timeSeconds.asString());
        timerLabel.setTextFill(Color.WHITESMOKE);
        timerLabel.setAlignment(Pos.CENTER);
        timerLabel.setEffect(ds);

        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(STARTTIME);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME+1),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();

        livesLabel.setText("Lives: " + Bomber.alive);
        livesLabel.setTextFill(Color.WHITESMOKE);
        livesLabel.setAlignment(Pos.CENTER_RIGHT);
        livesLabel.setEffect(ds);

        try {
            timerLabel.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 30));
            pointLabel.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 30));
            livesLabel.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 30));
        } catch (FileNotFoundException e) {
            timerLabel.setFont(new Font("Bungee Inline", 30));
            pointLabel.setFont(new Font("Bungee Inline", 30));
            livesLabel.setFont(new Font("Bungee Inline", 30));
        }

        GridPane bar = new GridPane();
        bar.setPrefWidth(BombermanGame.SCREEN_WIDTH);
        bar.setAlignment(Pos.TOP_LEFT);
        ColumnConstraints col1 = new ColumnConstraints(BombermanGame.SCREEN_WIDTH / 3.0);
        col1.setHalignment(HPos.LEFT);
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints(BombermanGame.SCREEN_WIDTH / 3.0);
        col2.setPercentWidth(25);
        col2.setHalignment(HPos.CENTER);
        ColumnConstraints col3 = new ColumnConstraints(BombermanGame.SCREEN_WIDTH / 3.0);
        col3.setPercentWidth(25);
        col3.setHalignment(HPos.RIGHT);
        bar.getColumnConstraints().addAll(col1,col2,col3);
        bar.addColumn( 0, pointLabel);
        bar.addColumn( 1, timerLabel);
        bar.addColumn( 2, livesLabel);
        bar.setBackground(new Background(new BackgroundFill(Color.GRAY,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        return bar;
    }

    public void resetTimer() {
        timeSeconds.set(STARTTIME);
    }

    public int getTimeSeconds() {
        return timeSeconds.get();
    }

    public void setPoints(int points) {
        pointLabel.setText("Points: " + points);
    }
}
