package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;


public class FlameSegment extends AnimatedEntitiy {	//TODO: BEFORE: extends Entity
	protected boolean _last;
	protected int _direction;
	protected Board _board;
	/**
	 *
	 * @param x
	 * @param y
	 * @param direction
	 * @param last cho biết segment này là cuối cùng của Flame hay không,
	 *                segment cuối có sprite khác so với các segment còn lại
	 */
	public FlameSegment(int x, int y, int direction, boolean last, Board board) {
		_x = x;
		_y = y;
		_last = last;
		_direction = direction;
		_board = board;
		switch (direction) {
			case 0:
				if(!_last) {
					_sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, _animate, 60);
				} else {
					_sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, _animate, 60);
				}
			break;
			case 1:
				if(!_last) {
					_sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, _animate, 60);
				} else {
					_sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, _animate, 60);
				}
				break;
			case 2:
				if(!_last) {
					_sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, _animate, 60);
				} else {
					_sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, _animate, 60);
				}
				break;
			case 3: 
				if(!_last) {
					_sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, _animate, 60);
				} else {
					_sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, _animate, 60);
				}
				break;

		}

	}
	
	@Override
	public void render(Screen screen) {
		switch (_direction) {
			case 0:
				if(!_last) {
					_sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, _animate, 60);
				} else {
					_sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, _animate, 60);
				}
				break;
			case 1:
				if(!_last) {
					_sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, _animate, 60);
				} else {
					_sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, _animate, 60);
				}
				break;
			case 2:
				if(!_last) {
					_sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, _animate, 60);
				} else {
					_sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, _animate, 60);
				}
				break;
			case 3:
				if(!_last) {
					_sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, _animate, 60);
				} else {
					_sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, _animate, 60);
				}
				break;

		}
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		Entity e = _board.getEntityAt((double)_x, (double)_y);
		if(e instanceof LayeredEntity)
			e = ((LayeredEntity) e).getTopEntity();
		screen.renderEntityWithBelowSprite(xt, yt , this, e.getSprite());
	}
	
	@Override
	public void update() {
		animate();
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi FlameSegment va chạm với Character
		if(e instanceof Character) {
			if(e.getXTile()==_x&&e.getYTile()==_y)
				((Character) e).kill();
		}
		if(e instanceof Bomb) {
			e.collide(this);
		}
		return false;
	}
	

}