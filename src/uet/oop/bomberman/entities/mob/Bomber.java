package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Input.Keyboard;
import uet.oop.bomberman.entities.Items.Items;
import uet.oop.bomberman.entities.Items.PowerupBombs;
import uet.oop.bomberman.entities.Items.PowerupFlames;
import uet.oop.bomberman.entities.Items.PowerupSpeed;
import uet.oop.bomberman.entities.Items.*;
import uet.oop.bomberman.entities.Tile.Barriers;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.mob.enemies.Enemies;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.Volume;

public class Bomber extends Mob {
    public static int x_pos;
    public static int y_pos;

    public static int score = 0;
    public static int alive = 3;
    public static boolean canPassWall = false;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        maxFlame = 3;
        spriteFlame = new Sprite[4][4];
        flame = 0;
        destroyAnimated = new Sprite[3];
        spriteFlame[0][0] = Sprite.player_up;
        spriteFlame[0][1] = Sprite.player_up_1;
        spriteFlame[0][2] = Sprite.player_up_2;
        spriteFlame[1][0] = Sprite.player_right;
        spriteFlame[1][1] = Sprite.player_right_1;
        spriteFlame[1][2] = Sprite.player_right_2;
        spriteFlame[2][0] = Sprite.player_down;
        spriteFlame[2][1] = Sprite.player_down_1;
        spriteFlame[2][2] = Sprite.player_down_2;
        spriteFlame[3][0] = Sprite.player_left;
        spriteFlame[3][1] = Sprite.player_left_1;
        spriteFlame[3][2] = Sprite.player_left_2;
        destroyAnimated[0] = Sprite.player_dead1;
        destroyAnimated[1] = Sprite.player_dead2;
        destroyAnimated[2] = Sprite.player_dead3;

    }

    public static void reset() {
        canPassWall = false;
        //score = 0;
        Bomb.setExplosionSize(1);
        BombermanGame.defaultBombRate = 1;
        BombermanGame.setBombRate(1);
    }

    public void calculateMove() {
        if (!isDead) {
            int xa = 0, ya = 0;
            if(Keyboard.up) ya--;
            else if(Keyboard.down) ya++;
            else if(Keyboard.left) xa--;
            else if(Keyboard.right) xa++;
            else flame = 0;
            if(xa != 0 || ya != 0)  {
                move(xa * speed, ya * speed);
            }
            this.setImg(spriteFlame[direction][flame].getFxImage());
        }
    }

    public boolean canMove(double _x, double _y) {
        return super.canMove(_x, _y);
    }

    public void move(double xa, double ya) {
        int current = direction;
        if(xa > 0) direction = 1;
        if(xa < 0) direction = 3;
        if(ya > 0) direction = 2;
        if(ya < 0) direction = 0;
        if(current != direction) flame = 0;
        else flame++;
        flame %= maxFlame;
        if(canMove(0, ya)) { //separate the moves for the player can slide when is colliding
            if((ya > 0 && y >= BombermanGame.SCREEN_HEIGHT / 2) || (ya < 0 && Map.getHEIGHT() - y >= BombermanGame.SCREEN_HEIGHT / 2)) {
                BombermanGame.increaseScreenPosY(-ya);
            }
            y += ya;
        }

        if(canMove(xa, 0)) {
            if((xa > 0 && x >= BombermanGame.SCREEN_WIDTH / 2) || (xa < 0 && Map.getWIDTH() - x >= BombermanGame.SCREEN_WIDTH / 2)) {
                BombermanGame.increaseScreenPosX(-xa);
            }
            x += xa;
        }
    }

    public void afterKill() {
        if(isDead) {
            if(flame >= maxFlame) {
                removed = true;
                --alive;
                if(alive == 0) {
                    BombermanGame.isFinish = true;
                } else {
                    Volume.stageTheme.stop();
                    BombermanGame.newGame = true;
                    Map.currentLevel--;
                    BombermanGame.timeForDisplayNextLevel = BombermanGame.maxTimeForDisplayNextLevel;
                }
                Bomber.reset();
                System.out.println("YES");
            }
            else {
                Volume.justDied.play();
                this.setImg(destroyAnimated[flame].getFxImage());
            }
            flame++;
        }
    }

    public void handleBomb() {
        if(Keyboard.space && BombermanGame.getBombRates() > 0) {
            Entity entity = Entity.getBarriers(getXTile(), getYTile(), this);
            if(!(entity instanceof Barriers)) {
                Bomb bomb = new Bomb(getXTile(), getYTile(), Sprite.bomb.getFxImage());
                BombermanGame.addBomb(bomb);
                Volume.bombPlaced.play();
                BombermanGame.addBombRates(-1);
            }
            Keyboard.space = false;
        }
    }

    public void handleConflictWithEnemies() {
        Entity entity = Entity.getMob(getXTile(), getYTile(), this);
        if(entity instanceof Enemies) {
            isDead = true;
        }
    }

    public void handleItems() {
        Entity entity = Entity.getItem(getXTile(), getYTile(), this);
        if(entity instanceof Items) {
            Volume.powerUp.play();
            if(entity instanceof PowerupSpeed) {
                speed += ((PowerupSpeed)entity).getAddSpeed();
                if(speed > maxSpeed) {
                    speed = maxSpeed;
                }
            }
            else if(entity instanceof PowerupFlames) {
                Bomb.increaseExplosionSize(1);
            } else if(entity instanceof PowerupBombs) {
                BombermanGame.addBombRates(1);
                BombermanGame.defaultBombRate += 1;
            } else if(entity instanceof PowerupWallPass) {
                canPassWall =  true;
            } else if(entity instanceof PowerupBombPass) {
                passBombAbility = true;
            }
            entity.setRemoved(true);
        }
    }

    public void handleTimerOut() {
        if (BombermanGame.gameBar.getTimeSeconds() == 0) {
            isDead = true;
        }
    }

    @Override
    public void update() {
        if(isDead) {
            afterKill();
            return;
        }
        x_pos = getXTile();
        y_pos = getYTile();
        calculateMove();
        handleBomb();
        handleConflictWithEnemies();
        handleItems();
        handleTimerOut();
    }
}
