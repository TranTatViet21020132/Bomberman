package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.Input.Keyboard;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.Volume;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public static final int SCREEN_WIDTH = 25 * Sprite.SCALED_SIZE;
    public static final int SCREEN_HEIGHT = 13 * Sprite.SCALED_SIZE;
    public static int defaultBombRate = 1;
    private static double screenPosX = 0;
    private static double screenPosY = 0;
    protected static int bombRate = 1;
    private static List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private static List<Entity> bombList = new ArrayList<>();
    private final int framesPerSec = 15; // Desired frames per second. You can modify this value!
    private final long nSecPerFrame = Math.round(1.0 / framesPerSec * 1e9);
    public static boolean isRunning = false;
    private GraphicsContext gc;
    private static Canvas canvas;
    private long startTime;
    public static boolean newGame = false;
    public static Menu menu;
    public static GameBar gameBar;
    public static boolean isFinish = false;
    private final static String FONT_PATH = "res/fonts/kenvector_future.ttf";
    private TranslateTransition transition = new TranslateTransition();
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public static void addEntity(Entity entity) {
        entities.add(entity);
    }

    public static void addStillObjects(Entity object) {
        stillObjects.add(object);
    }

    public static List<Entity> getEntities() {
        return entities;
    }

    public static List<Entity> getStillObjects() {
        return stillObjects;
    }

    public static void addBomb(Entity entity) {
        bombList.add(entity);
    }


    public static int getBombRates() {
        return bombRate;
    }

    public static void addBombRates(int i) {
        bombRate += i;
    }
    public static final int maxTimeForDisplayNextLevel = 180;
    public static int timeForDisplayNextLevel = maxTimeForDisplayNextLevel;

    public static String getFontPath() {
        return FONT_PATH;
    }

    public GameBar getGameBar() {
        return gameBar;
    }


    public void displayNextLevel(Stage stage) {
        String content = "Level";
        content += " " + (Map.currentLevel +  1);
        Label label = new Label(content);
        try {
            label.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 50));
        } catch (FileNotFoundException e) {
            label.setFont(Font.font("Bungee Inline", 50));
        }
        label.setStyle("-fx-text-alignment: center; -fx-alignment: center; -fx-background-color: black; -fx-text-fill: white");
        label.setMinWidth(SCREEN_WIDTH);
        label.setMinHeight(SCREEN_HEIGHT);
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(label);
        Scene scene1 = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene1);
    }
    public void startNextLevel(Stage stage) {
        if(Map.currentLevel >= Map.maxLevel) {
            isRunning = false;
            isFinish = true;
            newGame = false;
            return;
        }
        if(timeForDisplayNextLevel > 0) {
            --timeForDisplayNextLevel;
            displayNextLevel(stage);
            isRunning = false;
            return;
        }
        createLevel(stage);
    }

    public void createLevel(Stage stage) {
        Map.currentLevel++;
        entities.clear();
        stillObjects.clear();
        Keyboard.reset();
        Map.createMap();
        bombRate = defaultBombRate;
        gameBar = new GameBar();
        canvas = new Canvas(Map.getWIDTH(), Map.getHEIGHT());
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        TranslateTransition transition1 = new TranslateTransition();
        GridPane gameCanvas = new GridPane();
        gameCanvas.add(gameBar.createGameBar(), 0, 0);
        gameCanvas.add(canvas,0 , 1);
        root.getChildren().add(gameCanvas);
        transition.setNode(canvas);
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        scene.setOnKeyPressed(Keyboard::keyPressed);
        scene.setOnKeyReleased(Keyboard::keyReleased);
        stage.setScene(scene);
        menu.setGameScene(scene);
        screenPosX = 0;
        screenPosY = 0;
        isRunning = true;
        newGame = false;
    }

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image("resources/game_icon.png"));
        stage.setTitle("Bomberman");
        menu = new Menu(stage);
        menu.createStartMenu();
        menu.display();
        transition.setDuration(Duration.millis(75));
        AnimationTimer frameRateMeter = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if(isFinish) {
                    isFinish = false;
                    Volume.stageTheme.stop();
                    Volume.stageClear.stop();
                    Volume.stageStart.stop();
                    menu.createFinishGameMenu(Bomber.alive > 0);
                    menu.display();
                    Volume.titleScreen.stop();
                } else if(newGame) {
                    startNextLevel(stage);
                    if (timeForDisplayNextLevel == 0) {
                        Volume.stageStart.stop();

                        Volume.stageTheme.play();
                        Volume.stageTheme.loop();
                    }
                }
                if(isRunning) {
                    transition.setToX(screenPosX);
                    transition.setToY(screenPosY);
                    transition.play();
                    if (now - lastUpdate > nSecPerFrame) {
                        if (startTime < 0)
                            startTime = now;
                        update();
                        render();
                        lastUpdate = now;
                    }
                }
            }
        };
        startTime = -1;
        frameRateMeter.start();
    }

    public static void setBombRate(int bombRate) {
        BombermanGame.bombRate = bombRate;
    }

    public static void increaseScreenPosX(double delta) {
        if(delta != 0) {
            screenPosX += delta;
            if(screenPosX >= 0) {
                screenPosX = 0;
            }
            double maxWidth = -(Map.getWIDTH() - SCREEN_WIDTH);
            if(screenPosX <= maxWidth) {
                screenPosX = maxWidth;
            }
        }
    }

    public static void increaseScreenPosY(double delta) {
        if(delta != 0) {
            screenPosY += delta;
            if(screenPosY >= 0) {
                screenPosY = 0;
            }
            double maxHeight = -(Map.getHEIGHT() - SCREEN_HEIGHT);
            if(screenPosY <= maxHeight) {
                screenPosY = maxHeight;
            }
        }
    }

    public void update() {
        entities.removeIf(entity -> entity.isRemoved());
        entities.forEach(Entity::update);
        stillObjects.removeIf(entity -> entity.isRemoved());
        stillObjects.forEach(Entity::update);
        for (Entity entity : bombList) {
            entities.add(entity);
        }
        bombList.clear();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
