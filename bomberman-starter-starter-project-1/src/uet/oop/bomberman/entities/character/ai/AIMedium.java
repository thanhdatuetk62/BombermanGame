package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;

// using for Oneal Enemy
public class AIMedium extends AI
{
    public AIMedium(Board board)
    {
        this.board = board;
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
