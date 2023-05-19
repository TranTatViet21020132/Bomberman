package uet.oop.bomberman.entities.mob.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemies{
    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        direction = 0;
        speed = 0.4 * Sprite.SCALED_SIZE;
        ai = new AIMedium();
        spriteFlame[0][0] = Sprite.minvo_left1;
        spriteFlame[0][1] = Sprite.minvo_left2;
        spriteFlame[0][2] = Sprite.minvo_left3;
        spriteFlame[1][0] = Sprite.minvo_right1;
        spriteFlame[1][1] = Sprite.minvo_right2;
        spriteFlame[1][2] = Sprite.minvo_right3;
        deadAnimated = Sprite.minvo_dead;
        score = 700;
    }

    @Override
    public void update() {
        super.update();
    }
}
