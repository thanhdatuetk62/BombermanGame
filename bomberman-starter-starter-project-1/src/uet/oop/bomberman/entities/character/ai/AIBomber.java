package uet.oop.bomberman.entities.character.ai;

import uet.oop.bomberman.Board;

public class AIBomber extends AI {
    public AIBomber(Board board) {
        this.board = board;
    }
    public int calculateDirection() {
        return 0;
    }
}
