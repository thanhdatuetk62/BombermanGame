package uet.oop.bomberman.entities.character.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.library.Pair;
import uet.oop.bomberman.library.Queue;

public abstract class AI
{
    protected Random random = new Random();
    protected Board board;
    protected HashMap<Character, Boolean> canGo;
    protected char[][] map;
    protected int[] hX = {1, 0, 0, -1};
    protected int[] hY = {0, 1, -1, 0};
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
        ArrayList<Character> items = new ArrayList<Character>()
        {{
            add('#');
            add(' ');
            add('*');
            add('x');
            add('p');
            add('1');
            add('2');
            add('3');
            add('4');
            add('5');
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
        return (0 <= u && u < Game.levelHeight && 0 <= v && v < Game.levelWidth);
    }

    //init inDanger
    private void calcInDanger()
    {
        inDanger = new boolean[m][n];
        int[][] d = new int[m][n];
        Queue<Pair> queue = new Queue<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (map[i][j] == '4' || map[i][j] == '5')
                {
                    queue.add(new Pair(i, j));
                    inDanger[i][j] = true;
                    d[i][j] = 0;
                } else inDanger[i][j] = false;
        while (!queue.isEmpty())
        {
            Pair top = queue.remove();
            int u = top.getX();
            int v = top.getY();
            for (int j = 0; j < 4; j++)
            {
                for (int i = 1; i <= Game.getBombRadius(); i++)
                {
                    int x = u + hX[j] * i;
                    int y = v + hY[j] * i;
                    if (!validate(x, y)) break;
                    if (map[x][y] == '#') break;
                    if (map[x][y] == '*' || map[x][y] == 'p' || map[x][y] == 'x' || map[x][y] == '2' || map[x][y] == '3' || map[x][y] == '4' || map[x][y] == '5')
                    {
                        inDanger[x][y] = true;
                        break;
                    }
                    inDanger[x][y] = true;
                }
            }
        }
    }

    // caculate Danger distance
    abstract public void calcDangerDistance();

    // caculate distance from Enemy
    private void calcDistanceFromEnemy()
    {
        distanceFromEnemy = new int[m][n];
        Queue<Pair> queue = new Queue<>();
        // caculate for Balloon(1) and Oneal(2)
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (map[i][j] == '2' || map[i][j] == '3')
                {
                    queue.add(new Pair(i, j));
                    distanceFromEnemy[i][j] = 0;
                } else distanceFromEnemy[i][j] = -1;
        while (!queue.isEmpty())
        {
            Pair top = queue.remove();
            for (int i = 0; i < 4; i++)
            {
                int u = top.getX() + hX[i];
                int v = top.getY() + hY[i];
                if (!validate(u, v)) continue;
                if (map[u][v] == '2' || map[u][v] == '3' || map[u][v] == '4' || map[u][v] == '#' || map[u][v] == '*')
                    continue;
                if (distanceFromEnemy[u][v] >= 0) continue;
                distanceFromEnemy[u][v] = distanceFromEnemy[top.getX()][top.getY()] + 1;
                queue.add(new Pair(u, v));
            }
        }

        // caculate for Doria (4)
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (map[i][j] == '4')
                {
                    queue.add(new Pair(i, j));
                    distanceFromEnemy[i][j] = 0;
                }
        while (!queue.isEmpty())
        {
            Pair top = queue.remove();
            for (int i = 0; i < 4; i++)
            {
                int u = top.getX() + hX[i];
                int v = top.getY() + hY[i];
                if (!validate(u, v)) continue;
                if (map[u][v] == '2' || map[u][v] == '3' || map[u][v] == '4' || map[u][v] == '#')
                    continue;
                if (distanceFromEnemy[u][v] != -1 && distanceFromEnemy[u][v] <= distanceFromEnemy[top.getX()][top.getY()] + 1)
                    continue;
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
            //System.out.println("FALL");
            map = null;
            return;
        }
        map = new char[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                map[i][j] = temp[j][i];
    }

    public abstract int calculateDirection();

}
