package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;

public class AIMedium extends AI
{
    public AIMedium(Board board)
    {
        this.board = board;
    }

    @Override
    public int calculateDirection()
    {
        // TODO: cài đặt thuật toán tìm đường đi
        return 1;
    }

}
