/**
 *
 * @Author: longhoang08 - Long Hoang Bao
 */
 
package uet.oop.bomberman.entities.character.ai;

import java.util.ArrayList;
import java.util.Queue;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.tile.item.Item;

public abstract class AIEnemy extends AI
{
    public AIEnemy()
    {
        ArrayList<Character> items= new ArrayList<Character>() {{
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
        ArrayList<Character> canGoThrought = new ArrayList<Character>() {{
            add('-');
            add('x');
            add('p');
            add('b');
            add('f');
            add('s');
        }};
    }

    // caculate best Direction by distance
    protected int bestDirection()
    {
        int m = Game.levelWidth;
        int n = Game.levelHeight;
        int sX, sY;
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
            {
                //if (map[i][j] == 'p')
                {
                    sX = i;
                    sY = j;
                    break;
                }
            }
        return  1;
    }

}
