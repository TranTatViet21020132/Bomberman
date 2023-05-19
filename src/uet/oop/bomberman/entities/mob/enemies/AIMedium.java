package uet.oop.bomberman.entities.mob.enemies;

import javafx.util.Pair;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Tile.Brick;
import uet.oop.bomberman.entities.mob.Bomber;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.LinkedList;
import java.util.List;

public class AIMedium extends AI {

    private int dx[] = {1, 0, -1, 0};
    private int dy[] = {0, 1, 0, -1};
    private Pair<Integer, Integer>[][] parent = new Pair[101][101];
    private boolean[][] used = new boolean[101][101];
    private int direction;
    private boolean findBomber = false;

    private int maxDistance = 10;

    public AIMedium() {
        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j <= 100; j++) {
                parent[i][j] = new Pair<>(i, j);
            }
        }
    }

    public void reset() {
        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j <= 100; j++) {
                parent[i][j] = new Pair<>(i, j);
            }
        }
        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j <= 100; j++) {
                used[i][j] = false;
            }
        }
    }

    public void bfs(int x, int y, Enemies enemy) {
        reset();
        List<Pair<Integer, Integer>> q = new LinkedList<>();
        q.add(new Pair<>(x, y));
        used[x][y] = true;
        while (!q.isEmpty()) {
            Pair<Integer, Integer> p = q.get(0);
            q.remove(0);
            for (int i = 0; i < 4; i++) {
                int x1 = p.getKey() + dx[i];
                int y1 = p.getValue() + dy[i];
                if (x1 >= 0 && x1 < Map.getWIDTH() / Sprite.SCALED_SIZE && y1 >= 0 && y1 < Map.getHEIGHT() / Sprite.SCALED_SIZE) {
                    Entity entity = Entity.getBarriers(x1, y1, null);
                    Entity entity1 = Entity.getBomb(x1, y1, null);
                    if (entity instanceof Brick && enemy instanceof Kondoria) {
                        entity = null;
                    }
                    if (entity == null && entity1 == null && !used[x1][y1]) {
                        used[x1][y1] = true;
                        q.add(new Pair<>(x1, y1));
                        parent[x1][y1] = new Pair<>(p.getKey(), p.getValue());
                    }
                }
            }
        }
    }


    public int calculateDirection(int x_pos, int y_pos, boolean notMoving, Enemies enemy) {
        int x = (x_pos + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
        int y = (y_pos + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
        bfs(x, y, enemy);
        if (!findBomber) {
            notMoving = false;
        }
        if (used[Bomber.x_pos][Bomber.y_pos]) {
            int cnt = 0;
            findBomber = true;
            int i = Bomber.x_pos;
            int j = Bomber.y_pos;
            while (true) {
                ++cnt;
                int i1 = parent[i][j].getKey();
                int j1 = parent[i][j].getValue();
                if (i1 == x && j1 == y) {
                    break;
                }
                i = i1;
                j = j1;
            }
            if (enemy instanceof Minvo && cnt >= maxDistance) {
                findBomber = false;
                return direction;
            }
            int newDirection;
            if (i < x) {
                newDirection = 3;
            } else if (i > x) {
                newDirection = 1;
            } else if (j < y) {
                newDirection = 0;
            } else {
                newDirection = 2;
            }
            if (notMoving) {

                int deltaX = (int) Math.round(1.0 * (x_pos - x * Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE);
                int deltaY = (int) Math.round(1.0 * (y_pos - y * Sprite.SCALED_SIZE) / Sprite.SCALED_SIZE);
                if (direction == 0 || direction == 2) {

                    if (deltaX == 0) {
                        newDirection = 1;
                    } else {
                        newDirection = 3;
                    }
                } else {

                    if (deltaY == 0) {
                        newDirection = 0;
                    } else {
                        newDirection = 2;
                    }
                }
            }
            direction = newDirection;
        } else {
            findBomber = false;
        }
        return direction;
    }

    public boolean isFindBomber() {
        return findBomber;
    }

    @Override
    public int calculateDirection() {
        return random.nextInt(4);
    }
}
