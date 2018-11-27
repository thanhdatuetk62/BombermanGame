package uet.oop.bomberman.entities.character.enemy;


import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy
{

    public Oneal(int x, int y, Board board)
    {
        super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed() / 2, 200);

        _sprite = Sprite.oneal_left1;

        _ai = new AIMedium(board, this, true);
        _direction = _ai.calculateDirection();
    }

    @Override
    protected void chooseSprite()
    {
        //_direction = _ai.calculateDirection();
        switch (_direction)
        {
            case 0:
            case 1:
                if (_moving)
                    _sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
                else _sprite = Sprite.oneal_left1;
                break;
            case 2:
            case 3:
                if (_moving)
                    _sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
                else _sprite = Sprite.oneal_left1;
                break;
        }
    }
}
