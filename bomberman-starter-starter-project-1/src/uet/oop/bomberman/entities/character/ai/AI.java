package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.library.Pair;
import uet.oop.bomberman.library.Queue;

public abstract class AI
{
    protected boolean canMove = true;
    protected Random random = new Random();
    protected Board board;
    protected HashMap <Character, Boolean> canGo;
    protected char[][] map;
    protected int[] hX = {0, 1, -1,  0};
    protected int[] hY = {1, 0,  0, -1};
    protected int m, n;
    protected boolean[][] inDanger;
    protected int[][] dangerDistance, distanceFromEnemy;
    /**
     * Thuật toán tìm đường đi
     *
     * @return hướng đi xuống/phải/trái/lên tương ứng với các giá trị 0/1/2/3
     */

    public AI()
    {
        m = Game.levelHeight;
        n = Game.levelWidth;
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

    protected boolean validate(int u, int v)
    {
        return (0 <= u && u < Game.levelWidth && 0 <= v && v < Game.levelHeight);
    }

    //init inDanger
    private void calcInDanger()
    {
        inDanger = new boolean[m][n];
        int[][] d = new int[m][n];
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                d[i][j] = -1;
        Queue<Pair> queue = new Queue<>();
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                if (map[i][j] == '4' || map[i][j] == '5')
                {
                    queue.add(new Pair(i, j));
                    inDanger[i][j] = true;
                    d[i][j] = 0;
                }
                else inDanger[i][j] = false;
        while (!queue.isEmpty())
        {
            Pair top = queue.remove();
            for(int i = 0; i < 4; i++)
            {
                int u = top.getX();
                int v = top.getY();
                if (!validate(u, v)) continue;
                if (map[u][v] == '4' || map[u][v] == '#') continue;
                if (d[u][v] >= 0) continue;
                d[u][v] = d[top.getX()][top.getY()] + 1;
                inDanger[u][v] = true;
                if (!(map[u][v] == '2' || map[u][v] == '3' || map[u][v] == '5' || map[u][v] == '*'))
                    if (d[u][v] < Game.getBombRadius())
                    {
                        queue.add(new Pair(u, v));
                    }
            }
        }
    }

    // caculate Danger distance
    private void calcDangerDistance()
    {
        dangerDistance = new int[m][n];
        Queue<Pair> queue = new Queue<>();
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                if (map[i][j] != '#' && map[i][j] != '*' && map[i][j] != '1' && map[i][j] != '2' && map[i][j] != '3' && inDanger[i][j] == false)
                {
                    queue.add(new Pair(i, j));
                    dangerDistance[i][j] = 0;
                }
                else dangerDistance[i][j] = -1;
        while (!queue.isEmpty())
        {
            Pair top = queue.remove();
            for(int i = 0; i < 4; i++)
            {
                int u = top.getX();
                int v = top.getY();
                if (!validate(u, v)) continue;
                if (map[u][v] == '5' || map[u][v] == '2' || map[u][v] == '3' || map[u][v] == '4' || map[u][v] == '#' || map[u][v] == '*') continue;
                if (dangerDistance[u][v] >= 0) continue;
                dangerDistance[u][v] = dangerDistance[top.getX()][top.getY()] + 1;
                queue.add(new Pair(u, v));
            }
        }
    }

    // caculate distance from Enemy
    private void calcDistanceFromEnemy()
    {
        distanceFromEnemy = new int[m][n];
        Queue<Pair> queue = new Queue<>();
        // caculate for Balloon(1) and Oneal(2)
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                if (map[i][j] == '2' || map[i][j] == '3')
                {
                    queue.add(new Pair(i, j));
                    distanceFromEnemy[i][j] = 0;
                }
                else distanceFromEnemy[i][j] = -1;
        while (!queue.isEmpty())
        {
            Pair top = queue.remove();
            for(int i = 0; i < 4; i++)
            {
                int u = top.getX();
                int v = top.getY();
                if (!validate(u, v)) continue;
                if (map[u][v] == '5' || map[u][v] == '2' || map[u][v] == '3' || map[u][v] == '4' || map[u][v] == '#' || map[u][v] == '*') continue;
                if (distanceFromEnemy[u][v] >= 0) continue;
                distanceFromEnemy[u][v] = distanceFromEnemy[top.getX()][top.getY()] + 1;
                queue.add(new Pair(u, v));
            }
        }

        // caculate for Doria (4)
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                if (map[i][j] == '4')
                {
                    queue.add(new Pair(i, j));
                    distanceFromEnemy[i][j] = 0;
                }
        while (!queue.isEmpty())
        {
            Pair top = queue.remove();
            for(int i = 0; i < 4; i++)
            {
                int u = top.getX();
                int v = top.getY();
                if (!validate(u, v)) continue;
                if (map[u][v] == '5' || map[u][v] == '2' || map[u][v] == '3' || map[u][v] == '4' || map[u][v] == '#') continue;
                if (distanceFromEnemy[u][v] != -1 && distanceFromEnemy[u][v] <= distanceFromEnemy[top.getX()][top.getY()] + 1) continue;
                distanceFromEnemy[u][v] = distanceFromEnemy[top.getX()][top.getY()] + 1;
                queue.add(new Pair(u, v));
            }
        }
    }

    public void initDistace()
    {
        calcInDanger();
        calcDangerDistance();
        calcDistanceFromEnemy();
    }

    // Todo: @return current map
    public void calcCurrentMap() throws NullPointerException
    {
        if (this.board == null)
        {
            throw new NullPointerException("Board haven't been initialized!!!");
        }
        char[][] temp = this.board.reviveMap();
        if (temp == null)
        {
            System.out.println("FALL");
            map = null;
            return;
        }
        map = new char[m][n];
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                map[i][j] = temp[j][i];
        for(int i = 0; i < m; i++)
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
