package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;

public class AIBomber extends AI
{
    public AIBomber(Board board)
    {
        this.board = board;
    }

    @Override
    public void calcDangerDistance()
    {

    }

    public int calculateDirection()
    {
        calcCurrentMap();
        return random.nextInt(4);
    }
}
