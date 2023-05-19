package uet.oop.bomberman.graphics;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Items.*;
import uet.oop.bomberman.entities.Tile.Brick;
import uet.oop.bomberman.entities.Tile.Portal;
import uet.oop.bomberman.entities.Tile.Wall;
import uet.oop.bomberman.entities.mob.enemies.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    public static int enemies = 0;
    public static int currentLevel = -1;
    public static int maxLevel = 6;
    private static int WIDTH;
    private static int HEIGHT;
    private static char[][] matrix = new char[1001][1001];

    public static void createMap() {
        try {
            File file = new File("res/levels/Level" + currentLevel + ".txt");
            enemies = 0;
            Scanner sc = new Scanner(file);

            ArrayList<String> list = new ArrayList<>();
            while (sc.hasNextLine()) {
                list.add(sc.nextLine());
            }
            WIDTH = list.get(0).length() * Sprite.SCALED_SIZE;
            HEIGHT = list.size() * Sprite.SCALED_SIZE;
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                for (int j = 0; j < s.length(); j++) {
                    matrix[j][i] = s.charAt(j);
                    System.out.print(matrix[j][i]);
                    BombermanGame.addStillObjects((Entity) new Grass(j, i, Sprite.grass.getFxImage()));
                    Entity object;
                    if (s.charAt(j) == '#') {
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                    } else if (s.charAt(j) == '*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                    } else if (s.charAt(j) == 'x') {
                        object = new Portal(j, i, Sprite.portal.getFxImage());
                    } else if (s.charAt(j) == 'b') {
                        object = new PowerupBombs(j, i, Sprite.powerup_bombs.getFxImage());
                    } else if (s.charAt(j) == 'f') {
                        object = new PowerupFlames(j, i, Sprite.powerup_flames.getFxImage());
                    } else if (s.charAt(j) == 's') {
                        object = new PowerupSpeed(j, i, Sprite.powerup_speed.getFxImage());
                    } else if (s.charAt(j) == 'w') {
                        object = new PowerupWallPass(j, i, Sprite.powerup_wallpass.getFxImage());
                    } else if (s.charAt(j) == 'o') {
                        object = new PowerupBombPass(j, i, Sprite.powerup_bombpass.getFxImage());
                    } else if (s.charAt(j) == '1') {
                        object = new Balloom(j, i, Sprite.balloom_left1.getFxImage());
                    } else if (s.charAt(j) == '2') {
                        object = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                    } else if (s.charAt(j) == '3') {
                        object = new Doll(j, i, Sprite.doll_left1.getFxImage());
                    } else if (s.charAt(j) == '4') {
                        object = new Kondoria(j, i, Sprite.kondoria_left1.getFxImage());
                    } else if (s.charAt(j) == '5') {
                        object = new Minvo(j, i, Sprite.minvo_left1.getFxImage());
                    } else {
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                    }
                    if (object instanceof Enemies) {
                        ++enemies;
                        BombermanGame.addEntity(object);
                    } else {
                        BombermanGame.addStillObjects(object);
                    }
                    if (object instanceof Items || object instanceof Portal) {
                        BombermanGame.addStillObjects(new Brick(j, i, Sprite.brick.getFxImage()));
                    }
                }
                System.out.println();
            }
        } catch (FileNotFoundException f) {
            System.out.println(f);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static void setWIDTH(int WIDTH) {
        Map.WIDTH = WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void setHEIGHT(int HEIGHT) {
        Map.HEIGHT = HEIGHT;
    }

    public static char[][] getMatrix() {
        return matrix;
    }
}
