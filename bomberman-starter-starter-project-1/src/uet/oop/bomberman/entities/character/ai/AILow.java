package uet.oop.bomberman.entities.character.ai;

// using for Ballon Enemy
public class AILow extends AIEnemy
{
    public AILow() {
        super(false);
    }
    private int currentDirect;

    /*
        0 : down
        1 : ->
        2 : <-
        3 : up
     */

    @Override
    public void calcDangerDistance()
    {

    }

    @Override
    public int calculateDirection()
    {
        // TODO: cài đặt thuật toán tìm đường đi
        currentDirect = random.nextInt(4);
        return currentDirect;
    }
}
