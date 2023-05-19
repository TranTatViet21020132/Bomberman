package uet.oop.bomberman;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import uet.oop.bomberman.entities.mob.Bomber;

import java.io.*;
import java.util.*;

public abstract class SaveScore {
    public static boolean saved = false;
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Save your score");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);
        window.getIcons().add(new Image("resources/cup.png"));
        Label label = new Label();
        label.setText("Insert your name:");
        label.setAlignment(Pos.CENTER);
        try {
            label.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 40));
        } catch (FileNotFoundException e) {
            label.setFont(new Font("Bungee Inline", 40));
        }
        HBox box = new HBox(20);
        Button yesButton = new Button("YES");
        TextField text = new TextField();
        text.setMinWidth(100);
        text.setLayoutX(100);
        yesButton.setOnAction(event -> {
            saveScore(text);
            window.close();
        });
        yesButton.setOnMouseEntered(event -> {
            yesButton.setStyle("-fx-background-color: green");
        });
        yesButton.setOnMouseExited(event -> {
            yesButton.setStyle(null);
        });

        Button noButton = new Button("NO");
        try {
            noButton.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
            yesButton.setFont(Font.loadFont(new FileInputStream(BombermanGame.getFontPath()), 20));
        } catch (FileNotFoundException e) {
            noButton.setFont(Font.font("Bungee Inline", 20));
            yesButton.setFont(Font.font("Bungee Inline", 20));
        }
        noButton.setOnAction(event -> {
            window.close();
        });
        noButton.setOnMouseEntered(event -> {
            noButton.setStyle("-fx-background-color: red");
        });
        noButton.setOnMouseExited(event -> {
            noButton.setStyle(null);
        });
        box.getChildren().addAll(yesButton, noButton);
        box.setAlignment(Pos.CENTER);
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, text, box);
        Image backgroundImage = new Image("resources/scoreBackground2.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
        layout.setBackground(new Background(background));
        Scene scene = new Scene(layout);
        scene.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) {
                saveScore(text);
                window.close();
            }
        });
        window.setScene(scene);
        window.showAndWait();
    }

    public static void saveScore(TextField textField) {
        try{
            File file = new File("res/resources/scores.txt");
            Scanner sc = new Scanner(file);
            List<Pair<String, Integer>> list = new ArrayList<>();
            while(sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] arr = s.split(" ");
                String name = "";
                for(int i = 0; i < arr.length - 1; i++) {
                    name += arr[i];
                    if(i != arr.length - 2) {
                        name += " ";
                    }
                }
                int score = Integer.parseInt(arr[arr.length - 1]);
                name = name.trim();
                list.add(new Pair<>(name, score));
            }
            list.add(new Pair<>(textField.getText().trim(), Bomber.score));
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            String ans = "";
            for(int i = 0; i < list.size(); i++) {
                if(i >= 5) {
                    break;
                }
                ans += list.get(i).getKey() + " " + list.get(i).getValue();
                if(i != 4 || i != list.size() - 1) {
                    ans += "\n";
                }
            }
            FileWriter writer = new FileWriter("res/resources/scores.txt");
            writer.write(ans);
            saved = true;
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
