package uet.oop.bomberman.entities.mob.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemies{
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        direction = 0;
        ai = new AILow();
        spriteFlame[0][0] = Sprite.oneal_left1;
        spriteFlame[0][1] = Sprite.oneal_left2;
        spriteFlame[0][2] = Sprite.oneal_left3;
        spriteFlame[1][0] = Sprite.oneal_right1;
        spriteFlame[1][1] = Sprite.oneal_right2;
        spriteFlame[1][2] = Sprite.oneal_right3;
        deadAnimated = Sprite.oneal_dead;
        score = 200;
    }



    @Override
    public void update() {
        super.update();
    }
}
