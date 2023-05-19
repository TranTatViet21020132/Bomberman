package uet.oop.bomberman.Input;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.sounds.Volume;

public class Keyboard {
    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static boolean space;

    public static void reset() {
        BombermanGame.isRunning = false;
        up = down = left = right = space = false;
        BombermanGame.menu.display();
        Volume.titleScreen.stop();
        Volume.stageTheme.pause();
    }

    public static void keyPressed(javafx.scene.input.KeyEvent e) {
        switch (e.getCode()) {
            case UP:
                up = true;
                break;
            case DOWN:
                down = true;
                break;
            case RIGHT:
                right = true;
                break;
            case LEFT:
                left = true;
                break;
            case SPACE:
                space = true;
                break;
            case ESCAPE:
                reset();
                break;
            case EQUALS: case ADD:
                Volume.stageTheme.stop();
                if (BombermanGame.isFinish) {
                    Volume.stageStart.stop();
                } else {
                    Volume.stageStart.play();
                }
                BombermanGame.newGame = true;
                BombermanGame.timeForDisplayNextLevel = BombermanGame.maxTimeForDisplayNextLevel;
                break;
            case SUBTRACT: case MINUS:
                if(Map.currentLevel >= 1) {
                    Volume.stageTheme.stop();
                    Volume.stageStart.play();
                    BombermanGame.newGame = true;
                    Map.currentLevel-= 2;
                    BombermanGame.timeForDisplayNextLevel = BombermanGame.maxTimeForDisplayNextLevel;
                }
                break;
        }
    }

    public static void keyReleased(javafx.scene.input.KeyEvent e) {
        switch (e.getCode()) {
            case UP:
                up = false;
                break;
            case DOWN:
                down = false;
                break;
            case RIGHT:
                right = false;
                break;
            case LEFT:
                left = false;
                break;
            case SPACE:
                space = false;
                break;
        }
    }
}
