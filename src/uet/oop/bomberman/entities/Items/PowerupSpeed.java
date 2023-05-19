package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class PowerupSpeed extends Items{
    private double addSpeed = 0.05 * Sprite.SCALED_SIZE;

    public double getAddSpeed() {
        return addSpeed;
    }

    public PowerupSpeed(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}
