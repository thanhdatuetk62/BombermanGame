package uet.oop.bomberman.entities.character.ai;


public class AILow extends AI
{
	private int currentDirect;
	@Override
	public int calculateDirection()
	{
		// TODO: cài đặt thuật toán tìm đường đi
		currentDirect = random.nextInt(4);
		return currentDirect;
	}
}
