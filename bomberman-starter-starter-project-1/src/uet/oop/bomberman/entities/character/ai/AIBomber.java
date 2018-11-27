package uet.oop.bomberman.entities.character.ai;

import java.util.ArrayList;
import java.util.Random;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.library.Pair;
import uet.oop.bomberman.library.Queue;

public class AIBomber extends AI
{
    protected Bomber bomber;
    public AIBomber(Board board, Bomber bomber)
    {
        this.board = board;
        this.bomber = bomber;
        ArrayList<Character> canGoThrought = new ArrayList<Character>()
        {{
            add(' ');
            add('x');
            add('p');
            add('5');
            add('b');
            add('f');
            add('s');
            add('4');
            //add('*');
        }};
        for (char c : canGoThrought)
        {
            canGo.replace(c, true);
        }
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
                if (map[u][v] == '1' || map[u][v] == '2' || map[u][v] == '4' || map[u][v] == '3' || map[u][v] == '#' || map[u][v] == '*')
                    continue;
                if (dangerDistance[u][v] >= 0) continue;
                dangerDistance[u][v] = dangerDistance[top.getX()][top.getY()] + 1;
                queue.add(new Pair(u, v));
            }
        }
    }

    public int calculateDirection()
    {
        calcCurrentMap();
        initDistace();
        return bestDirection();
    }

    private int[][] bfs()
    {
        int d[][] = new int[m][n];
        Queue <Pair> queue = new Queue<>();
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                if ('1' <= map[i][j] && map[i][j] <= '3')
                {
                    queue.add(new Pair(i, j));
                    d[i][j] = 0;
                }
                else d[i][j] = -1;
        while (!queue.isEmpty())
        {
            Pair top = queue.remove();
            for(int i = 0; i < 4; i++)
            {
                int u = top.getX() + hX[i];
                int v = top.getY() + hY[i];
                if (!validate(u, v)) continue;
                if (d[u][v] != -1) continue;
                if (!canGo.get(map[u][v])) continue;
                d[u][v] = d[top.getX()][top.getY()] + 1;
                if (map[u][v] == '5' || map[u][v] == 'p') continue;
                queue.add(new Pair(u, v));
            }
        }
        return d;

    }

    private int bestDirection()
    {
        int x = -1, y = -1;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
            {
                if (map[i][j] == 'p' || map[i][j] == '5')
                {
                    x = i;
                    y = j;
                    break;
                }
            }

        // if bomber in danger, avoid Enemy
        if (inDanger[x][y])
        {
            int direction = -1;
            int curAns = m * n;
            int curDistance = dangerDistance[x][y];
            if (curDistance == -1)
            {
                System.out.println("CHET ME ROI");
                return random.nextInt(4);
            }
            int[][] d = bfs();
            boolean ok = false;

            System.out.println("DEBUG TIME!!!!");
            for(int i = 0; i < m; i++)
            {
                for(int j = 0; j < n; j++)
                    System.out.print(map[i][j]);
                System.out.println();
            }
            System.out.println();


            System.out.println("Danger Distance");
            for(int i = 0; i < n; i++)  System.out.printf("%2d ",i);
            System.out.println();
            for(int i = 0; i < m; i++)
            {
                for(int j = 0; j < n; j++)
                    System.out.printf("%2d ",dangerDistance[i][j]);
                System.out.println();
            }
            System.out.println();
            System.out.println();

            System.out.println("Distence Enemy");
            for(int i = 0; i < n; i++)  System.out.printf("%2d ",i);
            System.out.println();
            for(int i = 0; i < m; i++)
            {
                for(int j = 0; j < n; j++)
                    System.out.printf("%2d ",distanceFromEnemy[i][j]);
                System.out.println();
            }
            System.out.println();
            System.out.println();

            for(int i = 0; i < 4; i++)
            {
                int u = x + hX[i];
                int v = y + hY[i];
                if (!validate(u, v)) continue;
                if (dangerDistance[u][v] == -1) continue;
                if (dangerDistance[u][v] < curDistance)
                {
                    curDistance = dangerDistance[u][v];
                    curAns = distanceFromEnemy[u][v];
                    direction = i;
                }
                else if (dangerDistance[u][v] == curDistance)
                {
                    if (ok)
                    {
                        if (curAns > distanceFromEnemy[u][v] || curAns == -1)
                        {
                            curAns = distanceFromEnemy[u][v];
                            direction = i;
                        }
                    }
                    else
                    {
                        if (curAns < distanceFromEnemy[u][v])
                        {
                            curAns = distanceFromEnemy[u][v];
                            direction = i;
                        }
                    }
                }
            }

            return direction;
        }
        else
        {
            int[][] d = bfs();
            int curDistance = d[x][y];
            System.out.println(curDistance);
            if (curDistance <= Game.getBombRadius() + 1 && bomber.canPlaceBomb())
            {
                return 4;
            }
            else if (curDistance <= Game.getBombRadius() + 1)
            {
                // tim cach chay xa khoi Enemy
                int direction = -1;
                curDistance = distanceFromEnemy[x][y];
                for(int i = 0; i < 4; i++)
                {
                    int u = x + hX[i];
                    int v = y + hY[i];
                    if (!validate(u, v)) continue;
                    if (inDanger[u][v]) continue;
                    if (distanceFromEnemy[u][v] == -1) continue;
                    if (distanceFromEnemy[u][v] > curDistance)
                    {
                        curDistance = distanceFromEnemy[u][v];
                        direction = i;
                    }
                }
                if (direction != -1)
                    return  direction;
                return random.nextInt(4);
            }
            int direction = -1;
            boolean boom = false;
            for(int i = 0; i < 4; i++)
            {
                int u = x + hX[i];
                int v = y + hY[i];
                if (!validate(u, v)) continue;
                if (inDanger[u][v]) continue;
                if (d[u][v] == -1) continue;
                if (d[u][v] < curDistance)
                {
                    curDistance = d[u][v];
                    direction = i;
                    if (map[u][v] == '*')
                        boom = true;
                    else boom = false;
                }
            }
            if (boom == true)
                return 4;

            curDistance = d[x][y];
            for(int i = 0; i < 4; i++)
            {
                int u = x + hX[i];
                int v = y + hY[i];
                if (!validate(u, v)) continue;
                if (inDanger[u][v]) continue;
                if (d[u][v] == -1) continue;
                if (d[u][v] > curDistance)
                {
                    curDistance = d[u][v];
                    direction = i;
                }
            }
            //if (direction == -1) direction = random.nextInt(4);
            return direction;
        }
    }

}
