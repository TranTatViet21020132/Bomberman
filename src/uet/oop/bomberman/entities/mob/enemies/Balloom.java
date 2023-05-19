package uet.oop.bomberman.entities.mob.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemies {
    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        direction = 0;
        ai = new AILow();
        spriteFlame[0][0] = Sprite.balloom_left1;
        spriteFlame[0][1] = Sprite.balloom_left2;
        spriteFlame[0][2] = Sprite.balloom_left3;
        spriteFlame[1][0] = Sprite.balloom_right1;
        spriteFlame[1][1] = Sprite.balloom_right2;
        spriteFlame[1][2] = Sprite.balloom_right3;
        deadAnimated = Sprite.balloom_dead;
        score = 100;
    }

    @Override
    public void update() {
        super.update();
    }
}
