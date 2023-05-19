package uet.oop.bomberman.model;

import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Menu;
import uet.oop.bomberman.sounds.Volume;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SpaceRunnerSubScene extends SubScene {
    private final static String BACKGROUND_IMAGE = "resources/red_panel.png";
    private List<Pane> remindersList = new ArrayList<>();
    private boolean isHidden;

    public SpaceRunnerSubScene(Menu.menu option) {
        super(new AnchorPane(), 600, 400);
        prefWidth(600);
        prefHeight(400);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,
                600, 400, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));
        isHidden = true;
        setLayoutX(BombermanGame.SCREEN_WIDTH);
        setLayoutY(200);
        switch (option) {
            case HELP:
                root2.getChildren().add(createHelpContent());
                break;
            case SCORES:
                GridPane box = createScoresContent();
                if (box != null) {
                    root2.getChildren().add(box);
                }
                break;
            case CREDITS:
                createReminders();
                root2.getChildren().add(createCreditsContent());
                moveSubScene();
                break;
            case OPTIONS:
                root2.getChildren().add(createOptionsContent());
                break;
        }
    }

    public SpaceRunnerSubScene(int width, int height, boolean isWin, String img) {
        super(new AnchorPane(), width, height);
        prefWidth(width);
        prefHeight(height);
        BackgroundImage image = new BackgroundImage(new Image(img, width, height, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        if (isWin) {
            HBox box = new HBox(10);
            box.setLayoutX(50);
            box.setStyle("-fx-text-alignment: center");
            Label label = createLabel("Congratulations on winning the game!",
                    40, 400, width - 100, "-fx-text-alignment: center;");
            box.getChildren().add(label);
            root2.getChildren().add(label);
        }
    }

    public SpaceRunnerSubScene(Parent parent, double v, double v1) {
        super(parent, v, v1);
    }

    public SpaceRunnerSubScene(Parent parent, double v,
                               double v1, boolean b, SceneAntialiasing sceneAntialiasing) {
        super(parent, v, v1, b, sceneAntialiasing);
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);
        if (isHidden) {
            transition.setToX(-700);
            isHidden = false;
        } else {
            transition.setToX(700);
            isHidden = true;
        }
        transition.play();
    }

    public Label createLabel(String text, int size, String style) {
        Label label = new Label(text);
        try {
            label.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), size));
        } catch (FileNotFoundException e) {
            label.setFont(new Font("Bungee Inline", size));
        }
        label.setMaxWidth(500);
        label.setWrapText(true);
        label.setGraphicTextGap(30);
        label.setStyle(style);
        return label;
    }

    public Label createLabel(String text, int size, int minWidth, String style) {
        Label label = createLabel(text, size, style);
        label.setMinWidth(minWidth);
        return label;
    }

    public Label createLabel(String text, int size, int minWidth, int maxWidth, String style) {
        Label label = createLabel(text, size, minWidth, style);
        label.setMaxWidth(maxWidth);
        return label;
    }

    public VBox createHelpContent() {
        Label label = createLabel("How to play:", 30, null);
        Label label1 = createLabel("Move Left", 20, null);
        ImageView leftIcon = new ImageView(new Image("resources/key_left.png", 20, 20, false, true));
        label1.setGraphic(leftIcon);
        Label label2 = createLabel("Move Right", 20, null);
        ImageView rightIcon = new ImageView(new Image("resources/key_right.png", 20, 20, false, true));
        label2.setGraphic(rightIcon);
        Label label3 = createLabel("Move Up", 20, null);
        ImageView upIcon = new ImageView(new Image("resources/key_up.png", 20, 20, false, true));
        label3.setGraphic(upIcon);
        Label label4 = createLabel("Move down", 20, null);
        ImageView downIcon = new ImageView(new Image("resources/key_down.png", 20, 20, false, true));
        label4.setGraphic(downIcon);
        Label label5 = createLabel("Place bomb", 20, null);
        ImageView spaceIcon = new ImageView(new Image("resources/key_space.png", 30, 30, false, true));
        label5.setGraphic(spaceIcon);
        Label label6 = createLabel("Pause game", 20, null);
        ImageView escapeIcon = new ImageView(new Image("resources/key_esc.png", 30, 30, false, true));
        label6.setGraphic(escapeIcon);
        Label label7 = createLabel("Next level", 20, null);
        ImageView nextIcon = new ImageView(new Image("resources/key_equals.png", 15, 15, false, true));
        label7.setGraphic(nextIcon);
        Label label8 = createLabel("Previous level", 20, null);
        ImageView prevIcon = new ImageView(new Image("resources/key_minus.png", 15, 15, false, true));
        label8.setGraphic(prevIcon);
        GridPane instruction = new GridPane();
        instruction.setAlignment(Pos.CENTER);
        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints(265);
            column.setHalignment(HPos.CENTER);
            instruction.getColumnConstraints().add(column);
        }
        for (int i = 0; i < 4; i++) {
            RowConstraints row = new RowConstraints(60);
            row.setValignment(VPos.CENTER);
            instruction.getRowConstraints().add(row);
        }
        instruction.add(label1, 0, 0);
        instruction.add(label2, 0, 1);
        instruction.add(label3, 0, 2);
        instruction.add(label4, 0, 3);
        instruction.add(label5, 1, 0);
        instruction.add(label6, 1, 1);
        instruction.add(label7, 1, 2);
        instruction.add(label8, 1, 3);
        try {
            label.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 30));
            label1.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
            label2.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
            label3.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
            label4.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
            label5.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
            label6.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
            label7.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
            label8.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
        } catch (FileNotFoundException e) {
            label.setFont(new Font("Bungee Inline", 30));
            label1.setFont(new Font("Bungee Inline", 20));
            label2.setFont(new Font("Bungee Inline", 20));
            label3.setFont(new Font("Bungee Inline", 20));
            label4.setFont(new Font("Bungee Inline", 20));
            label5.setFont(new Font("Bungee Inline", 20));
            label6.setFont(new Font("Bungee Inline", 20));
            label7.setFont(new Font("Bungee Inline", 20));
            label8.setFont(new Font("Bungee Inline", 20));
        }
        VBox box = new VBox(10);
        box.getChildren().addAll(label, instruction);
        box.setLayoutX(50);
        box.setLayoutY(50);
        return box;
    }

    public GridPane createScoresContent() {
        try {
            GridPane grid = new GridPane();
            File file = new File("res/resources/scores.txt");
            Scanner sc = new Scanner(file);
            grid.setLayoutX(50);
            grid.setLayoutY(50);
            Label text = createLabel("Top highest scores:", 30, null);
            try {
                text.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
            } catch (FileNotFoundException e) {
                text.setFont(new Font("Bungee Inline", 20));
            }
            String style = "-fx-border-style: dotted; -fx-padding: 5 5 5 5; -fx-border-width: 1 1 0 0; -fx-text-alignment: right";
            grid.add(text, 0, 0, 3, 1);
            grid.setStyle("-fx-border-style: solid; ");
            grid.setAlignment(Pos.CENTER);
            grid.add(createLabel("RANK", 20, 50, style),0, 1);
            grid.add(createLabel("NAME", 20, 300, style), 1, 1);
            grid.add(createLabel("SCORE", 20, 150, style), 2, 1);
            int cnt = 2;
            while(sc.hasNextLine()) {
                if(cnt > 6) {
                    break;
                }
                String s = sc.nextLine();
                String[] arr = s.split(" ");
                String name = "";
                String score = "";
                for (int i = 0; i < arr.length - 1; i++) {
                    name += arr[i];
                    if (i != arr.length - 2) {
                        name += " ";
                    }
                }
                score = arr[arr.length - 1];
                grid.add(createLabel(Integer.toString(cnt - 1), 20, style), 0, cnt);
                grid.add(createLabel(name, 20, style), 1, cnt);
                grid.add(createLabel(score, 20, style), 2, cnt);
                ++cnt;
            }
            while (cnt <= 6) {
                grid.add(createLabel(Integer.toString(cnt - 1), 20, style), 0, cnt);
                grid.add(createLabel("", 20, style), 1, cnt);
                grid.add(createLabel("", 20, style), 2, cnt);
                ++cnt;
            }
            return grid;
        } catch (FileNotFoundException fileException) {
            System.out.println(fileException);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    public VBox createOptionsContent()  {
        VBox soundOption = new VBox(10);
        Label label = createLabel("VOLUME", 40, null);
        try {
            label.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 40));
        } catch (FileNotFoundException e) {
            label.setFont(new Font("Bungee Inline", 40));
        }
        Volume volume = new Volume();
        soundOption.setLayoutX(40);
        soundOption.setLayoutY(40);
        soundOption.getChildren().addAll(label, volume.volumeChange());
        return soundOption;
    }

    public void setRandomReminder() {
        if (isHidden) {
            return;
        }
        Random random = new Random();
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.getChildren().clear();
        root2.getChildren().add(remindersList.get(random.nextInt(remindersList.size())));
    }

    public VBox createCreditsContent() {
        VBox box = new VBox(10);
        box.setLayoutX(50);
        box.setLayoutY(50);
        Label label = createLabel("Group 8 welcome you to our game!", 30, null);
        Label label1 = createLabel("Group mates: ", 25, null);
        Label label2 = createLabel("  -  Tran Tat Viet", 20, null);
        Label label3 = createLabel("  -  Pham Tung Thuy", 20, null);
        box.getChildren().addAll(label, label1, label2, label3);
        return box;
    }

    public void createReminders() {
        remindersList.add(createReminder("Kondoria can pass through breakable blocks.",
                "sprites/kondoria_left1.png"));
        remindersList.add(createReminder("Minvo will start chasing if the two distance is lower than 10 blocks.",
                "sprites/minvo_left1.png"));
        remindersList.add(createReminder("Despite the slow speed, Kondoria will always track you down.",
                "sprites/kondoria_left1.png"));
        remindersList.add(createReminder("You can destroy bricks using bombs.",
                "sprites/brick.png"));
        remindersList.add(createReminder("Balloom only moves randomly.",
                "sprites/balloom_left1.png"));
        remindersList.add(createReminder("Doll will always find a way to catch you, as it is smart.",
                "sprites/doll_left1.png"));
        remindersList.add(createReminder("Hidden behind the bricks lies the portal.",
                "sprites/portal.png"));
        remindersList.add(createReminder("Portal can only opened if all enemies have been killed.",
                "sprites/portal.png"));
    }

    public Pane createReminder(String msg, String img) {
        VBox box = new VBox(10);
        box.setLayoutX(50);
        box.setLayoutY(50);
        box.getChildren().add(createLabel("Did you know:", 40, null));
        Label label = createLabel(msg, 20, null);
        ImageView icon = new ImageView(new Image(img, 40, 40, false, true));
        label.setGraphic(icon);
        box.getChildren().add(label);
        return box;
    }
}
