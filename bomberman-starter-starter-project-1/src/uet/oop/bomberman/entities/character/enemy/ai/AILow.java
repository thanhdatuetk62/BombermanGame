package uet.oop.bomberman.entities.character.enemy.ai;


public class AILow extends AI {
	private int currentDirect = random.nextInt(4);
	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
		currentDirect = random.nextInt(4);
		return currentDirect;
	}
}
