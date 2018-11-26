package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.library.Pair;

// using for Oneal Enemy
public class AIMedium extends AIEnemy
{
    Pair start;

    public AIMedium(Board board, Pair start)
    {
        this.board = board;
        this.start = start;
    }

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
        calcCurrentMap();
        if (map == null) return 1;
        initDistace();
        return 1;
    }

}
