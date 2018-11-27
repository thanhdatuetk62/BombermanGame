package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.library.Pair;
import uet.oop.bomberman.library.Queue;

// using for Oneal Enemy
public class AIMedium extends AIEnemy
{
    Enemy enemy;
    boolean canChangeSpeed = false;
    public AIMedium(Board board, Enemy enemy, boolean b)
    {
        super(b);
        this.canChangeSpeed = b;
        canGo.replace('2', true);
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
                if (map[u][v] == '5' || map[u][v] == '1' || map[u][v] == '3' || map[u][v] == '4' || map[u][v] == '#' || map[u][v] == '*')
                    continue;
                if (dangerDistance[u][v] >= 0) continue;
                dangerDistance[u][v] = dangerDistance[top.getX()][top.getY()] + 1;
                queue.add(new Pair(u, v));
            }
        }
    }

    @Override
    public int calculateDirection()
    {
        // TODO: cài đặt thuật toán tìm đường đi
        calcCurrentMap();
        if (map == null) return 1;
        initDistace();
        if(canChangeSpeed) {
            if(allowSpeedUp)
                enemy.setSpeed(Game.getBomberSpeed()*2/3);
            else
                enemy.setSpeed(Game.getBomberSpeed()/2);    //TODO: TEST
        }
        return bestDirection(enemy.getXTile(), enemy.getYTile());
    }
}
