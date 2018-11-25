package uet.oop.bomberman.entities.character.ai;

import java.util.Random;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;

public abstract class AI
{
    protected boolean canMove = true;
    protected Random random = new Random();
    protected Board board;

    /**
     * Thuật toán tìm đường đi
     *
     * @return hướng đi xuống/phải/trái/lên tương ứng với các giá trị 0/1/2/3
     */

    // Todo: @return current map
    public char[][] getCurrentMap() throws NullPointerException
    {
        if (this.board == null)
        {
            throw new NullPointerException("Board haven't been initialized!!!");
        }
        char[][] map = board.reviveMap();

        return  map;
    }

    public abstract int calculateDirection();
}
