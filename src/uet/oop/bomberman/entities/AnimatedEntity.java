package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class AnimatedEntity extends Entity {
    protected int flame = 0;
    protected int maxFlame = 3;
    protected Sprite[] animated;
    protected Sprite[] destroyAnimated;
    public AnimatedEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public abstract void update();
}
