package uet.oop.bomberman.entities.mob.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;


public class Enemies extends Mob {
    protected final int maxDelay = 1;
    protected final int maxStep = 10;
    protected AI ai;
    protected int delayCount = maxDelay;
    protected int step = maxStep;
    protected boolean changeDirection = true;
    protected Sprite deadAnimated;
    protected int imageFlame = 0;
    protected int score = 0;
    protected boolean notMoving = false;

    public Enemies(int x, int y, Image img) {
        super(x, y, img);
        flame = 0;
        maxFlame = 3;
        spriteFlame = new Sprite[3][3];
        destroyAnimated = new Sprite[3];
        destroyAnimated[0] = Sprite.mob_dead1;
        destroyAnimated[1] = Sprite.mob_dead2;
        destroyAnimated[2] = Sprite.mob_dead3;
    }

    @Override
    public void kill(boolean dead) {
        this.setImg(deadAnimated.getFxImage());
        super.kill(dead);
    }

    @Override
    public void afterKill() {
        if (flame >= maxFlame) {
            removed = true;
            Bomber.score += score;
            BombermanGame.gameBar.setPoints(Bomber.score);
            Map.enemies--;
        } else {
            this.setImg(destroyAnimated[flame].getFxImage());
        }
        flame++;
    }

    @Override
    public void update() {
        if (isDead) {
            afterKill();
            return;
        }
        calculateMove();

    }

    @Override
    public void calculateMove() {
        if (delayCount > 0) {
            --delayCount;
            return;
        }
        delayCount = maxDelay;
        if(ai instanceof AIMedium) {
           int newDirection = ((AIMedium) ai).calculateDirection(x, y, notMoving, this);
           if(((AIMedium)ai).isFindBomber()) {
               direction = newDirection;
           }
        }
        if ((ai instanceof AILow) || (ai instanceof AIMedium && !((AIMedium) ai).isFindBomber())) {
            if (changeDirection) {
                direction = ai.calculateDirection();
            }
        }
        int xa = 0;
        int ya = 0;
        if (direction == 0) --ya;
        else if (direction == 1) {
            imageFlame = 1;
            ++xa;
        } else if (direction == 2) ++ya;
        else if (direction == 3) {
            imageFlame = 0;
            --xa;
        }
        move(xa * speed, ya * speed);
        this.setImg(spriteFlame[imageFlame][flame].getFxImage());

    }

    @Override
    public void move(double xa, double ya) {
        ++flame;
        flame %= maxFlame;
        changeDirection = true;
        if (canMove(xa, ya)) {
            notMoving = false;
            changeDirection = false;
            if (step <= 0) {
                changeDirection = true;
                step = maxStep;
            } else {
                --step;
            }
            x += xa;
            y += ya;
        } else {
            notMoving = true;
        }
    }

    @Override
    public boolean canMove(double _x, double _y) {
        return super.canMove(_x, _y);
    }
}
