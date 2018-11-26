package uet.oop.bomberman.entities.character.ai;

import java.util.ArrayList;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.library.Pair;
import uet.oop.bomberman.library.Queue;

public abstract class AIEnemy extends AI
{
    public AIEnemy()
    {
        ArrayList<Character> canGoThrought = new ArrayList<Character>() {{
            add('-');
            add('x');
            add('p');
            add('b');
            add('f');
            add('s');
        }};
        for(char c : canGoThrought)
        {
            canGo.replace(c, true);
        }
    }


    // caculate best Direction by distance
    protected int bestDirection(int _x, int _y)
    {

        int sX = -1, sY = -1;
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
            {
                if (map[i][j] == 'p' || map[i][j] == '5')
                {
                    sX = i;
                    sY = j;
                    break;
                }
            }
        Pair s = new Pair(sX, sY);
        Queue<Pair> queue = new Queue<Pair>();
        int[][] distance = new int[m][n];
        for(int i = 0; i < m; i++)
            for(int j = 0; j <= n; j++)
                distance[i][j] = -1;
        distance[sX][sY] = 0;
        queue.add(s);
        while (!queue.isEmpty())
        {
            Pair u = queue.remove();
            for(int i = 0; i < 4; i++)
            {
                int x = u.getX() + hX[i];
                int y = u.getY() + hY[i];
                if (!validate(x, y)) continue;
                if (distance[x][y] != -1) continue;
                distance[x][y] = distance[u.getX()][u.getY()] + 1;
                queue.add(new Pair(x, y));
            }
        }

        return 1;
    }

}
