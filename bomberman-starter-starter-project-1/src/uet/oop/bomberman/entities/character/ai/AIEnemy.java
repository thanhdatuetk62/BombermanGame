package uet.oop.bomberman.entities.character.ai;

import java.util.ArrayList;
import uet.oop.bomberman.library.Pair;
import uet.oop.bomberman.library.Queue;

public abstract class AIEnemy extends AI
{
    protected boolean speed;
    protected boolean allowSpeedUp = false;
    public AIEnemy(boolean speed)
    {
        this.speed = speed;
        ArrayList<Character> canGoThrought = new ArrayList<Character>()
        {{
            add(' ');
            add('x');
            add('p');
            add('5');
            add('b');
            add('f');
            add('s');
        }};
        for (char c : canGoThrought)
        {
            canGo.replace(c, true);
        }
    }

    // caculate best Direction by distance
    protected int bestDirection(int _y, int _x)
    {
        int sX = -1, sY = -1;
        for (int i = 0; i < m; i++) {
            boolean breakable = false;
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 'p' || map[i][j] == '5') {
                    sX = i;
                    sY = j;
                    breakable = true;
                    break;
                }
            }
            if(breakable) break;
            sX =0; sY = 0;
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
            if (curDistance == -1) return 0;
            for (int i = 0; i < 4; i++)
            {
                int x = _x + hX[i];
                int y = _y + hY[i];
                if (!validate(x, y)) continue;
                if (dangerDistance[x][y] == -1) continue;
                if (dangerDistance[x][y] < curDistance)
                {
                    curDistance = dangerDistance[x][y];
                    direction = i;
                    distanceToBomber = distance[x][y];
                } else if (dangerDistance[x][y] == curDistance)
                {
                    if (distanceToBomber == -1 || distanceToBomber > distance[x][y])
                    {
                        direction = i;
                        distanceToBomber = distance[x][y];
                    }
                }
            }
            if (direction == -1) direction = random.nextInt(4);
            allowSpeedUp = true;
            return direction;
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
            for (int i = 0; i < 4; i++)
                die[i] = 0;
            int curDistance = distance[_x][_y];
            for (int i = 0; i < 4; i++)
            {
                int x = _x + hX[i];
                int y = _y + hY[i];
                if (!validate(x, y))
                {
                    die[i] = 1;
                    continue;
                }
                ;
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
            if(curDistance < 4) allowSpeedUp = true;
            else allowSpeedUp = false;                  //TODO: TEST :))
            if (direction == -1)
            {
                for (int i = 0; i < 4; i++)
                    if (die[i] == 0) return i;
                for (int i = 0; i < 4; i++)
                    if (die[i] == 1) return i;
                return 0;
            } else return direction;
        }
    }

}
