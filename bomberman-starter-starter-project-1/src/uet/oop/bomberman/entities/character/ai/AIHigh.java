package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.library.Pair;
import uet.oop.bomberman.library.Queue;

// using for Doria Enemy
public class AIHigh extends AIEnemy
{

    Enemy enemy;

    public AIHigh(Board board, Enemy enemy)
    {
        canGo.replace('*', true);
        canGo.replace('3', true);
        this.board = board;
        this.enemy = enemy;
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
        if (map == null) return 1;
        initDistace();
        return bestDirection(enemy.getXTile(), enemy.getYTile());
    }

    @Override
    public void calcDangerDistance()
    {
        dangerDistance = new int[m][n];
        Queue<Pair> queue = new Queue<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (map[i][j] != '#' && map[i][j] != '*' && map[i][j] != '1' && map[i][j] != '2' && map[i][j] != '3' && inDanger[i][j] == false)
                {
                    queue.add(new Pair(i, j));
                    dangerDistance[i][j] = 0;
                } else dangerDistance[i][j] = -1;
        while (!queue.isEmpty())
        {
            Pair top = queue.remove();
            for (int i = 0; i < 4; i++)
            {
                int u = top.getX() + hX[i];
                int v = top.getY() + hY[i];
                if (!validate(u, v)) continue;
                if (map[u][v] == '5' || map[u][v] == '1' || map[u][v] == '2' || map[u][v] == '4' || map[u][v] == '#' || map[u][v] == '*')
                    continue;
                if (dangerDistance[u][v] >= 0) continue;
                dangerDistance[u][v] = dangerDistance[top.getX()][top.getY()] + 1;
                queue.add(new Pair(u, v));
            }
        }
    }

    @Override
    protected int bestDirection(int _y, int _x)
    {
        int sX = -1, sY = -1;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
            {
                if (map[i][j] == 'p' || map[i][j] == '5')
                {
                    sX = i;
                    sY = j;
                    break;
                }
            }
        if (sX == -1)
        {
            return 2;
        }
        Pair s = new Pair(sX, sY);
        Queue<Pair> queue = new Queue<Pair>();
        int[][] distance = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                distance[i][j] = -1;
        distance[sX][sY] = 0;
        queue.add(s);
        /*
        System.out.println("DEBUG TIME!!!!");
        for(int i = 0; i < m; i++)
        {
            for(int j = 0; j < n; j++)
                System.out.print(map[i][j]);
            System.out.println();
        }
        System.out.println();
        */
        while (!queue.isEmpty())
        {
            Pair u = queue.remove();
            for (int i = 0; i < 4; i++)
            {
                int x = u.getX() + hX[i];
                int y = u.getY() + hY[i];
                if (!validate(x, y)) continue;
                if (distance[x][y] != -1) continue;
                if (!canGo.get(map[x][y])) continue;
                distance[x][y] = distance[u.getX()][u.getY()] + 1;
                queue.add(new Pair(x, y));
            }
        }

        //slove if this enemy in danger
        //System.out.println(_x + " " + _y);
        if (inDanger[_x][_y])
        {
            int direction = -1;
            boolean canAlive = false;
            int curDistance = dangerDistance[_x][_y];
            int distanceToBomber = m * n;
            if (curDistance == -1)
                return 0;
            for(int i = 0; i < 4; i++)
            {
                int x = _x + hX[i];
                int y = _y + hY[i];
                if (!validate(x, y)) continue;
                if (dangerDistance[x][y] < curDistance)
                {
                    curDistance = dangerDistance[x][y];
                    direction = i;
                    distanceToBomber = distance[x][y];
                }
                else if (dangerDistance[x][y] == curDistance)
                {
                    if (distanceToBomber == -1 || distanceToBomber > distance[x][y])
                    {
                        direction = i;
                        distanceToBomber = distance[x][y];
                    }
                }
            }
            if (direction == -1) direction = 1;
            return  direction;
        }
        // or not, it will try to catch bomber
        else
        {
            /*
            System.out.println("x = " + _x + "y = " + _y);
            for(int i = 0; i < n; i++)  System.out.printf("%2d ",i);
            System.out.println();
            for(int i = 0; i < m; i++)
            {
                for(int j = 0; j < n; j++)
                    System.out.printf("%2d ",distance[i][j]);
                System.out.println();
            }
            System.out.println();
            System.out.println();
            */
            int direction = -1;
            int[] die = new int[4];
            for(int i = 0; i < 4; i++)
                die[i] = 0;
            int curDistance = distance[_x][_y];
            for(int i = 0; i < 4; i++)
            {
                int x = _x + hX[i];
                int y = _y + hY[i];
                if (!validate(x, y))
                {
                    die[i] = 1;
                    continue;
                };
                if (inDanger[x][y])
                {
                    die[i] = 2;
                    continue;
                }
                if (distance[x][y] == -1) continue;
                if (distance[x][y] < curDistance)
                {
                    curDistance = distance[x][y];
                    direction = i;
                }
            }
            if (direction == -1)
            {
                for(int i = 0; i < 4; i++)
                    if (die[i] == 0) return i;
                for(int i = 0; i < 4; i++)
                    if (die[i] == 1) return i;
                return 0;
            }
            else
                return  direction;
        }
    }
}
