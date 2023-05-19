package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Sprite;

public class Explosion extends AnimatedEntity {
    protected boolean last;
    protected Sprite sprite;
    public Explosion(int xUnit, int yUnit, Image img, int direction, boolean last) {
        super(xUnit, yUnit, img);
        this.last = last;
        animated = new Sprite[3];
        destroyAnimated = new Sprite[3];
        maxFlame = 3;
        if(last) {
            switch (direction) {
                case 0:
                    animated[0] = Sprite.explosion_vertical_top_last;
                    animated[1] = Sprite.explosion_vertical_top_last1;
                    animated[2] = Sprite.explosion_vertical_top_last2;
                    break;
                case 1:
                    animated[0] = Sprite.explosion_horizontal_right_last;
                    animated[1] = Sprite.explosion_horizontal_right_last1;
                    animated[2] = Sprite.explosion_horizontal_right_last2;
                    break;
                case 2:
                    animated[0] = Sprite.explosion_vertical_down_last;
                    animated[1] = Sprite.explosion_vertical_down_last1;
                    animated[2] = Sprite.explosion_vertical_down_last2;
                    break;
                case 3:
                    animated[0] = Sprite.explosion_horizontal_left_last;
                    animated[1] = Sprite.explosion_horizontal_left_last1;
                    animated[2] = Sprite.explosion_horizontal_left_last2;
                    break;
            }
        }
        else {
            switch (direction) {
                case 0: case 2:
                    animated[0] = Sprite.explosion_vertical;
                    animated[1] = Sprite.explosion_vertical1;
                    animated[2] = Sprite.explosion_vertical2;
                    break;
                case 1: case 3:
                    animated[0] = Sprite.explosion_horizontal;
                    animated[1] = Sprite.explosion_horizontal1;
                    animated[2] = Sprite.explosion_horizontal2;
                    break;
            }
        }

        this.setImg(animated[0].getFxImage());
    }

    @Override
    public void update() {
        Entity entity = Entity.getMob(getXTile(), getYTile(), this);
        if (entity instanceof Mob) {
            ((Mob)entity).kill(true);
        }
        if(flame >= maxFlame) {
            removed = true;
            return;
        }
        this.setImg(animated[flame].getFxImage());
        ++flame;
    }
}
