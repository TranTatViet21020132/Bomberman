package uet.oop.bomberman.entities.mob.enemies;

public class AILow extends AI{
    @Override
    public int calculateDirection() {
        return random.nextInt(4);
    }
}
