package uet.oop.bomberman.entities.mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tile.Barriers;
import uet.oop.bomberman.entities.Tile.Brick;
import uet.oop.bomberman.entities.Tile.Portal;
import uet.oop.bomberman.entities.mob.enemies.Kondoria;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.Volume;

public abstract class Mob extends AnimatedEntity {
    protected int direction = 1;
    protected double speed = 0.3 * Sprite.SCALED_SIZE;
    protected final double maxSpeed = 0.5 * Sprite.SCALED_SIZE;
    protected Sprite[][] spriteFlame;
    protected boolean isDead = false;
    protected boolean canPassBomb = false;
    protected boolean passBombAbility = false;
    public Mob(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    public void kill(boolean dead) {
        flame = 0;
        isDead = dead;
    }

    public abstract void afterKill() throws InterruptedException;

    @Override
    public abstract void update();

    public abstract void calculateMove();

    public abstract void move(double xa, double ya);

    public boolean canMove(double _x, double _y) {
        Entity bomb = getBomb(getXTile(), getYTile(), this);
        if(bomb instanceof Bomb || passBombAbility) canPassBomb = true;
        else canPassBomb = false;
        for (int c = 0; c < 4; c++) { //colision detection for each corner of the player
            double xt = ((_x + x) + c % 2 * 11 - Sprite.scale * 3) / Sprite.SCALED_SIZE; //divide with tiles size to pass to tile coordinate
            double yt = ((_y + y) - c / 2 * 12 + Sprite.scale * 5 + (c == 0 && c % 2 == 0 ? -15 : 0)) / Sprite.SCALED_SIZE; //these values are the best from multiple tests
            Entity entity = getBarriers((int)(Math.round(xt)), (int)(Math.round(yt)), this);
            Entity entity1 = getBomb((int)(Math.round(xt)), (int)(Math.round(yt)), this);
            if(entity instanceof Barriers) {
                if(this instanceof Bomber && Bomber.canPassWall && entity instanceof Brick) {
                    return true;
                }
                if(entity instanceof Portal && this instanceof Bomber && Map.enemies == 0) {
                    BombermanGame.newGame = true;
                    BombermanGame.timeForDisplayNextLevel = BombermanGame.maxTimeForDisplayNextLevel;
                    return true;
                }
                if(!(this instanceof Kondoria && (entity instanceof Brick || entity instanceof Portal))) {
                    return false;
                }
            }
            if(entity1 instanceof Bomb && !canPassBomb) return false;
        }

        return true;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
