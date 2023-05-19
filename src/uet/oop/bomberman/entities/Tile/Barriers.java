package uet.oop.bomberman.entities.Tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Barriers extends Entity {
    public Barriers(int x, int y, Image img) {super(x, y, img);}
    @Override
    public abstract void update();
}
