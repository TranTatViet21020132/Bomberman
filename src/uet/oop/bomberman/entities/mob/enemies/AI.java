package uet.oop.bomberman.entities.mob.enemies;

import java.util.Random;

public abstract class AI {
    protected Random random = new Random();

    public abstract int calculateDirection();
}
