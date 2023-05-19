package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Tile.Barriers;
import uet.oop.bomberman.entities.Tile.Brick;
import uet.oop.bomberman.entities.mob.Mob;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sounds.Volume;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends AnimatedEntity {
    private int count_down;
    private static int explosionSize = 1;
    private boolean isExploded = true;
    private int[] dx = {0, 1, 0, -1};
    private int[] dy = {-1, 0, 1, 0};
    private int[] dir = {0, 1, 2, 3};
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        animated = new Sprite[3];
        destroyAnimated = new Sprite[3];
        animated[0] = Sprite.bomb;
        animated[1] = Sprite.bomb_1;
        animated[2] = Sprite.bomb_2;
        destroyAnimated[0] = Sprite.bomb_exploded;
        destroyAnimated[1] = Sprite.bomb_exploded1;
        destroyAnimated[2] = Sprite.bomb_exploded2;
        count_down = 30;
    }
    public void handleExploded() {
        if(flame >= maxFlame) {
            removed = true;
            BombermanGame.addBombRates(1);
            return;
        }
        this.setImg(destroyAnimated[flame].getFxImage());
        Volume.bombExplosion.play();
        ++flame;
    }

    public static void increaseExplosionSize(int x) {
        explosionSize += x;
    }
    public static void setExplosionSize(int x) {
        explosionSize = x;
    }
    public void handleExplosion() {
        for (int j = 0; j < 4; j++) {
            List<Pair<Integer, Integer>> coordinateList = new ArrayList<>();
            for(int i = 1; i <= explosionSize; i++) {
                int x1 = this.getXTile() + i * dx[j];
                int y1 = this.getYTile() + i * dy[j];
                if (x1 >= 0 && x1 < Map.getWIDTH() && y1 >= 0 && y1 < Map.getHEIGHT()) {
                    Entity entity = Entity.getBarriers(x1, y1, this);
                    Entity bomb = Entity.getBomb(x1, y1, this);
                    if (entity instanceof Barriers) {
                        if (entity instanceof Brick) {
                            ((Brick) entity).setDestroy(true);
                        }
                        break;
                    }
                    if(bomb instanceof  Bomb) {
                        ((Bomb)bomb).count_down = 0;
                    }
                    coordinateList.add(new Pair<>(x1, y1));
                }
            }
            for(int i = 0; i < coordinateList.size(); i++) {
                boolean last = false;
                if(i == coordinateList.size() - 1) {
                    last = true;
                }
                BombermanGame.addStillObjects(new Explosion(coordinateList.get(i).getKey(), coordinateList.get(i).getValue(), Sprite.bomb.getFxImage(), dir[j], last));
            }
        }
    }

    public void bombExploded() {
        count_down = 0;
        flame = 0;
        Entity e = Entity.getMob(this.getXTile(), this.getYTile(), this);
        if(e instanceof Mob) {
            ((Mob) e).kill(true);
        }
        handleExplosion();
        handleExploded();
    }

    @Override
    public void update() {
        if(count_down == 0) {
            if(isExploded) {
                bombExploded();
                isExploded = false;
                return;
            }
            handleExploded();
        } else if(count_down > 0) {
            ++flame;
            flame %= maxFlame;
            this.setImg(animated[flame].getFxImage());
            --count_down;
        }
    }

}
