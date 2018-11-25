package uet.oop.bomberman.entities.character.ai;

import java.util.Random;
import uet.oop.bomberman.Board;

public abstract class AI {
	protected boolean canMove = true;
	protected Random random = new Random();
	protected Board board;

	public AI()
	{
	}

	/**
	 * Thuật toán tìm đường đi
	 * @return hướng đi xuống/phải/trái/lên tương ứng với các giá trị 0/1/2/3
	 */
	public abstract int calculateDirection();
}
