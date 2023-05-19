package uet.oop.bomberman;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.model.SpaceRunnerButton;
import uet.oop.bomberman.model.SpaceRunnerSubScene;
import uet.oop.bomberman.sounds.Volume;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu {
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private Scene gameScene;
    private final int moveDistance = 100;
    private final int MENU_BUTTONS_START_X = (BombermanGame.SCREEN_WIDTH - SpaceRunnerButton.getButtonWidth()) / 2;
    private final int MENU_BUTTONS_START_Y = 150;
    private final int FINISH_BUTTONS_START_X = (BombermanGame.SCREEN_WIDTH - SpaceRunnerButton.getButtonWidth()) / 2;
    private final int FINISH_BUTTONS_START_Y = 200;
    private List<SpaceRunnerButton> menuButtons;
    private List<SpaceRunnerButton> finishGameButtons = new ArrayList<>();
    private final DropShadow ds = new DropShadow();
    private HashMap<String, SpaceRunnerSubScene> map;
    private List<SpaceRunnerSubScene> subScenes;
    private SpaceRunnerSubScene creditsSubScene;
    private boolean isHidden = true;
    private SpaceRunnerSubScene currentSubScene = null;
    public Menu(Stage stage) {
        menuButtons = new ArrayList<>();
        map = new HashMap<>();
        subScenes = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, BombermanGame.SCREEN_WIDTH, BombermanGame.SCREEN_HEIGHT);
        mainStage = stage;
        mainStage.setScene(mainScene);
    }

    public void createStartMenu() {
        createButton("PLAY", false);
        createButton("SCORES", false);
        createButton("HELP", false);
        createButton("OPTIONS", false);
        createButton("EXIT", false);
        createLogo();
        createSubScenes();
        createBackground();
        mainScene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE && gameScene != null) {
                BombermanGame.isRunning = true;
                mainStage.setScene(gameScene);
                Volume.stageTheme.continueClip();
            }
            Volume.titleScreen.stop();
        });
    }

    public void reset() {
        mainPane.getChildren().clear();
        menuButtons.clear();
        finishGameButtons.clear();
        map.clear();
        subScenes.clear();
        isHidden = true;
    }

    public void createFinishGameMenu(boolean win) {
        reset();
        createButton("REPLAY", true);
        createButton("SCORES", true);
        createButton("SAVE", true);
        createButton("EXIT", true);
        Label label = null;

        ds.setColor(Color.BLACK);
        ds.setRadius(5);
        ds.setOffsetX(3);
        ds.setOffsetY(3);

        if(win) {
            Volume.ending.play();
            Image backgroundImage = new Image("resources/win1.jpg", BombermanGame.SCREEN_WIDTH, BombermanGame.SCREEN_HEIGHT, false, true);
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
            mainPane.setBackground(new Background(background));
            label = new Label("Congratulations! You won!");
        } else {
            Volume.gameOver.play();
            label = new Label("You will win it next time!");
        }
        label.setText(label.getText() + "\nYour score: " + Bomber.score);
        label.setStyle("-fx-text-alignment: center; -fx-text-fill: white; -fx-alignment: center");
        label.setEffect(ds);
        try {
            label.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 40));
        } catch (FileNotFoundException e) {
            label.setFont(new Font("Bungee Inline", 40));
        }
        label.setWrapText(true);
        label.setMaxWidth(BombermanGame.SCREEN_WIDTH);
        label.setMinWidth(BombermanGame.SCREEN_WIDTH);
        label.setLayoutY(50);
        mainPane.getChildren().add(label);
    }


    public void addFinishGameButton(SpaceRunnerButton button) {
        button.setLayoutX(FINISH_BUTTONS_START_X);
        button.setLayoutY(FINISH_BUTTONS_START_Y + finishGameButtons.size() * 100);
        finishGameButtons.add(button);
        mainPane.getChildren().add(button);
    }


    public void addMenuButton(SpaceRunnerButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    public void setAllScenes() {
        for(SpaceRunnerButton button : menuButtons) {
            button.setOnAction(event -> {
                SpaceRunnerSubScene subScene = map.get(button.getText());
                if(subScene != null) {
                    hiddenSubScenes(subScene);
                    subScene.moveSubScene();
                }
            });
        }

    }

    public void createButton(String msg, boolean isFinishMenu) {
        SpaceRunnerButton button = new SpaceRunnerButton(msg);
        if(msg.equals("PLAY")) {
            button.setOnMouseClicked(event -> {
                BombermanGame.isRunning = true;
                if(button.getText().equals("PLAY")) {
                    setAllScenes();
                    mainPane.getChildren().add(creditsSubScene);
                    Volume.stageStart.play();
                    button.setText("RESUME");
                    BombermanGame.newGame = true;
                } else if (button.getText().equals("RESUME")) {
                    Volume.stageStart.stop();
                    Volume.stageTheme.continueClip();
                }
                mainStage.setScene(gameScene);
                Volume.titleScreen.stop();
            });
        } else if(msg.equals("EXIT")) {
            button.setOnMouseClicked(event -> {
                mainStage.close();
                Volume.titleScreen.stop();
                Volume.stageStart.stop();
            });
        } else if(msg.equals("REPLAY")) {
            button.setOnMouseClicked(event -> {
                Volume.ending.stop();
                Volume.gameOver.stop();
                reset();
                createStartMenu();
                for(SpaceRunnerButton otherButton : menuButtons) {
                    if(otherButton.getText().equals("PLAY")) {
                        otherButton.setText("RESUME");
                    }
                }
                Volume.ending.stop();
                BombermanGame.newGame = true;
                BombermanGame.isRunning = true;
                Map.currentLevel = 0;
                Volume.stageStart.play();
                Bomber.alive = 3;
            });
        } else if(msg.equals("SCORES") && isFinishMenu) {
            createScoresSubScene();
            button.setOnMouseClicked(event -> {
                moveButtons(finishGameButtons);
                isHidden = !isHidden;
            });
        } else if(msg.equals("SAVE")) {
            button.setOnMouseClicked(event -> {
                SaveScore.display();
                if(SaveScore.saved) {
                    resetScoreSubScene();
                    deleteSaveButton(button);
                    SaveScore.saved = false;
                    if(!isHidden) {
                        moveButtons(finishGameButtons);
                        isHidden = true;
                    }
                }
            });

        }
        if(isFinishMenu) {
            button.setOnAction(event -> {
                SpaceRunnerSubScene subScene = map.get(button.getText());
                if(subScene != null) {
                    hiddenSubScenes(subScene);
                    subScene.moveSubScene();
                }
            });
            addFinishGameButton(button);
        } else {
            button.setOnAction(event -> {
                SpaceRunnerSubScene subScene = map.get(button.getText());
                if(currentSubScene == null) {
                    isHidden = true;
                    moveButtons(menuButtons);
                    currentSubScene = subScene;
                } else if(currentSubScene.equals(subScene)){
                    currentSubScene = null;
                    isHidden = false;
                    moveButtons(menuButtons);
                } else {
                    currentSubScene = subScene;
                }
                if(subScene != null) {
                    hiddenSubScenes(subScene);
                    subScene.moveSubScene();
                }
            });
            addMenuButton(button);

        }
    }

    public void moveButtons(List<SpaceRunnerButton> list) {
        for(SpaceRunnerButton button1 : list) {
            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(0.3));
            transition.setNode(button1);
            int distance = moveDistance - FINISH_BUTTONS_START_X;
            if(isHidden) {
                transition.setToX(distance);
            } else {
                transition.setToX(0);
            }
            transition.play();
        }
    }

    public void resetScoreSubScene() {
        if(map.containsKey("SCORES")) {
            mainPane.getChildren().remove(map.get("SCORES"));
            subScenes.remove(map.get("SCORES"));
            map.remove("SCORES");
        }
        createScoresSubScene();
    }

    public void deleteSaveButton(SpaceRunnerButton button) {
        finishGameButtons.remove(button);
        mainPane.getChildren().remove(button);
        for(int i = 0; i < finishGameButtons.size(); i++) {
            finishGameButtons.get(i).setLayoutY(FINISH_BUTTONS_START_Y + i * 100);
        }
    }

    public void createSubScenes() {
        createHelpSubScene();
        createScoresSubScene();
        createOptionsSubScene();
        createCreditsSubScene();
    }

    public void createHelpSubScene() {
        SpaceRunnerSubScene subScene = new SpaceRunnerSubScene(menu.HELP);
        mainPane.getChildren().add(subScene);
        map.put("HELP", subScene);
        subScenes.add(subScene);
    }

    public void createScoresSubScene() {
        SpaceRunnerSubScene subScene = new SpaceRunnerSubScene(menu.SCORES);
        mainPane.getChildren().add(subScene);
        map.put("SCORES", subScene);
        subScenes.add(subScene);

    }

    public void createCreditsSubScene() {
        creditsSubScene = new SpaceRunnerSubScene(menu.CREDITS);
    }

    public void createOptionsSubScene() {
        SpaceRunnerSubScene subScene = new SpaceRunnerSubScene(menu.OPTIONS);
        mainPane.getChildren().add(subScene);
        map.put("OPTIONS", subScene);
        subScenes.add(subScene);
    }

    public void hiddenSubScenes(SpaceRunnerSubScene subScene) {
        boolean check = true;
        for(SpaceRunnerSubScene scene : subScenes) {
            if(scene != subScene && !scene.isHidden()) {
                scene.moveSubScene();
                check = false;
            }
        }
        if(check && creditsSubScene != null) {
            creditsSubScene.moveSubScene();
            if(gameScene != null) {
                creditsSubScene.setRandomReminder();
            }
        }
    }

    public void createBackground() {
        try{
            Image backgroundImage = new Image("resources/game_bg.jpg", BombermanGame.SCREEN_WIDTH,
                    BombermanGame.SCREEN_HEIGHT, false, true);
            BackgroundImage background = new BackgroundImage(backgroundImage,
                    BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER, null);
            mainPane.setBackground(new Background(background));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createLogo() {
        ImageView logo = new ImageView("resources/logo.png");
        logo.setLayoutX(300);
        logo.setLayoutY(0);
        logo.setOnMouseEntered(event -> logo.setEffect(new DropShadow()));
        logo.setOnMouseExited(event -> logo.setEffect(null));
        mainPane.getChildren().add(logo);
    }

    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    public void display() {
        if(gameScene != null) {
            creditsSubScene.setRandomReminder();
        }
        mainStage.setScene(mainScene);
        mainStage.show();
        Volume.titleScreen.play();
    }

    public enum menu{
        HELP,
        SCORES,
        OPTIONS,
        CREDITS
    }
}
