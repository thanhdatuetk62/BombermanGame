package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.library.Pair;

// using for Doria Enemy
public class AIHigh extends AI
{
    Pair start;
    public AIHigh(Board board, Pair start)
    {
        canGo.replace('*', true);
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
        calcCurrentMap();
        if (map == null)
            return 1;
        initDistace();
        return 1;
    }

}
