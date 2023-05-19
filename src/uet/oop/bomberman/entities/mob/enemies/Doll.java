package uet.oop.bomberman.entities.mob.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemies {

    public Doll(int x, int y, Image img) {
        super(x, y, img);
        direction = 0;
        speed = 0.5 * Sprite.SCALED_SIZE;
        ai = new AIMedium();
        spriteFlame[0][0] = Sprite.doll_left1;
        spriteFlame[0][1] = Sprite.doll_left2;
        spriteFlame[0][2] = Sprite.doll_left3;
        spriteFlame[1][0] = Sprite.doll_right1;
        spriteFlame[1][1] = Sprite.doll_right2;
        spriteFlame[1][2] = Sprite.doll_right3;
        deadAnimated = Sprite.doll_dead;
        score = 1000;
    }

    @Override
    public void update() {
        super.update();
    }
}
