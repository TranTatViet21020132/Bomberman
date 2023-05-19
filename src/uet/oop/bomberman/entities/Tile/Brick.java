package uet.oop.bomberman.entities.Tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Barriers {
    protected Sprite[] destroyAnimated;
    protected int flame;
    protected int maxFlame;
    protected boolean isDestroy = false;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
        destroyAnimated = new Sprite[3];
        flame = 0;
        maxFlame = 3;
        destroyAnimated[0] = Sprite.brick_exploded;
        destroyAnimated[1] = Sprite.brick_exploded1;
        destroyAnimated[2] = Sprite.brick_exploded2;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }

    @Override
    public void update() {
        if(isDestroy) {
            if(flame >= maxFlame) removed = true;
            else {
                this.setImg(destroyAnimated[flame].getFxImage());
            }
            flame++;
        }
    }
}
