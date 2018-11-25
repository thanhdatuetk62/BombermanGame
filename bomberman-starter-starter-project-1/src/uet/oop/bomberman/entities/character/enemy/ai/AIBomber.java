package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;

public class AIBomber extends AI {
    private Board _board;
    public AIBomber(Board board) {
        _board = board;
    }
    //TODO: Khung đây, ông thêm biến vào nếu cần nhé
    public int calculateDirection() {

        return 0;
    }
}
