package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Items extends Entity {
    public Items(int x, int y, Image img) {super(x, y, img);}
    @Override
    public abstract void update();

}
