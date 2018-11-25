package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI
{
	public AIMedium(Board board)
	{
		this.board = board;
	}
	@Override
	public int calculateDirection()
	{
		// TODO: cài đặt thuật toán tìm đường đi
		return 1;
	}

}
