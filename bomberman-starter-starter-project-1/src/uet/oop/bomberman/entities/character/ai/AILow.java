package uet.oop.bomberman.entities.character.ai;

import java.util.ArrayList;

// using for Ballon Enemy
public class AILow extends AI
{
    private int currentDirect;

    /*
        0 : down
        1 : ->
        2 : <-
        3 : up
     */
    @Override
    public int calculateDirection()
    {
        // TODO: cài đặt thuật toán tìm đường đi
        currentDirect = random.nextInt(4);
        return currentDirect;
    }
}
