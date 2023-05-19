package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Items.Items;
import uet.oop.bomberman.entities.Tile.Barriers;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Iterator;

public abstract class Entity {
    protected int x;
    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected Image img;
    protected double width;
    protected double height;
    protected boolean removed = false;
    protected boolean isCancel;
    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        int size = Sprite.SCALED_SIZE;
        this.x = xUnit * size;
        this.y = yUnit * size;
        this.img = img;
        width = img.getWidth();
        height = img.getHeight();
        isCancel = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }


    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();


    public int getXTile() {
        return (int) ((x + width / 2) / Sprite.SCALED_SIZE);
    }
    public  int getYTile() {
        return (int) ((y + height / 2) / Sprite.SCALED_SIZE);
    }

    public static Entity getBarriers(int x, int y, Entity a) {
        Iterator<Entity> itr = BombermanGame.getStillObjects().iterator();
        Entity current;
        Entity ans = null;
        while (itr.hasNext()) {
            current = itr.next();
            if(current == a || !(current instanceof Barriers)) continue;
            if(current.getXTile() == x && current.getYTile() == y) {
                ans = current;
            }
        }
        return ans;
    }


    public static Entity getMob(int x, int y, Entity a) {
        Iterator<Entity> itr = BombermanGame.getEntities().iterator();
        Entity current;
        Entity ans = null;
        while (itr.hasNext()) {
            current = itr.next();
            if(current == a) continue;
            if(current.getXTile() == x && current.getYTile() == y && current instanceof Mob) {
                ans = current;
            }
        }
        return ans;
    }

    public static Entity getItem(int x, int y, Entity a) {
        Iterator<Entity> itr = BombermanGame.getStillObjects().iterator();
        Entity current;
        Entity ans = null;
        while(itr.hasNext()) {
            current = itr.next();
            if(current == a) continue;;
            if(current.getXTile() == x && current.getYTile() == y && current instanceof Items) {
                ans = current;
            }
        }
        return ans;
    }

    public static Entity getBomb(int x, int y, Entity a) {
        Iterator<Entity> itr = BombermanGame.getEntities().iterator();
        Entity current;
        Entity ans = null;
        while(itr.hasNext()) {
            current = itr.next();
            if(current == a) continue;;
            if(current.getXTile() == x && current.getYTile() == y && current instanceof Bomb){
                ans = current;
            }
        }
        return ans;
    }
}
