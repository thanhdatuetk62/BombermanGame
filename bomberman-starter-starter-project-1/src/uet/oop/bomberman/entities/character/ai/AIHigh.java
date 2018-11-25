package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

// using for Doria Enemy
public class AIHigh extends AI
{
    public AIHigh(Board board)
    {
        canGo.replace('*', true);
        this.board = board;
        caclCurrentMap();
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
        return 1;
    }

}
