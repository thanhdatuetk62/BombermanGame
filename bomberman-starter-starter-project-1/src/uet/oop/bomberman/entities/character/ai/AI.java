package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import uet.oop.bomberman.Game;

public abstract class AI
{
    protected boolean canMove = true;
    protected Random random = new Random();
    protected Board board;
    protected HashMap <Character, Boolean> canGo;
    protected char[][] map;
    protected int[] hX = {0, 1, -1,  0};
    protected int[] hY = {1, 0,  0, -1};
    int m, n;
    /**
     * Thuật toán tìm đường đi
     *
     * @return hướng đi xuống/phải/trái/lên tương ứng với các giá trị 0/1/2/3
     */

    public AI()
    {
        canGo = new HashMap<>();
        ArrayList<Character> items = new ArrayList<Character>() {{
            add('#');
            add('-');
            add('*');
            add('x');
            add('p');
            add('1');
            add('2');
            add('3');
            add('4');
            add('b');
            add('f');
            add('s');
        }};
        for (char c : items)
        {
            canGo.put(c, false);
        }
    }

    // Todo: @return current map
    public void caclCurrentMap() throws NullPointerException
    {
        if (this.board == null)
        {
            System.out.println("FALL");
            //throw new NullPointerException("Board haven't been initialized!!!");
        }
        map = this.board.reviveMap();
        m = Game.levelHeight;
        n = Game.levelWidth;
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public abstract int calculateDirection();
}
