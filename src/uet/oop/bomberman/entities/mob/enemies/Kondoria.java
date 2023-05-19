package uet.oop.bomberman.entities.mob.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemies {

    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        direction = 0;
        speed = 0.2 * Sprite.SCALED_SIZE;
        ai = new AIMedium();
        spriteFlame[0][0] = Sprite.kondoria_left1;
        spriteFlame[0][1] = Sprite.kondoria_left2;
        spriteFlame[0][2] = Sprite.kondoria_left3;
        spriteFlame[1][0] = Sprite.kondoria_right1;
        spriteFlame[1][1] = Sprite.kondoria_right2;
        spriteFlame[1][2] = Sprite.kondoria_right3;
        deadAnimated = Sprite.kondoria_dead;
        score = 500;
    }
    @Override
    public void update() {
        super.update();
    }
}
