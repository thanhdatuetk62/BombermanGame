package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.ai.AIHigh;
import uet.oop.bomberman.graphics.Sprite;

public class Doria extends Enemy {


    public Doria(int x, int y, Board board) {
        super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed() / 3, 1000);

        // Ông sửa cái sprite này nhé, sửa xong thì xóa cmt đi!!!
        _sprite = Sprite.balloom_left1;

        _ai = new AIHigh(board);
        _direction = _ai.calculateDirection();
    }
    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                _sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animate, 60);
                break;
            case 2:
            case 3:
                _sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 60);
                break;
        }
    }
}
