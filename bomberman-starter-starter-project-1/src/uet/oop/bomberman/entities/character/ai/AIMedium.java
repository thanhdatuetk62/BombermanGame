package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;

public class AIMedium extends AI {

	private int currentDirect;

	public AIMedium(Board board)
	{
		this.board = board;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
		currentDirect = random.nextInt(4);
		return currentDirect;
	}

}
