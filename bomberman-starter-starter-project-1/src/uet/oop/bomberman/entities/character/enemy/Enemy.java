package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Sound.Action;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;

public abstract class Enemy extends Character {

	protected int _points;
	
	protected double _speed;
	protected AI _ai;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;
	private int currentDirect = 0;
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}
	
	@Override
	public void update() {
		animate();
		
		if(!_alive) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
		// TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
		// TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
		// TODO: sử dụng move() để di chuyển
		// TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
		_moving = true;
		if(_steps != MAX_STEPS) {
			_steps--;
			if(_steps == 0)
				_steps = MAX_STEPS;
		} else {
		    _steps--;
			currentDirect = _ai.calculateDirection();
		}
		if(currentDirect==0) move(_x, _y + _speed);	//DOWN
		if(currentDirect==1) move(_x + _speed, _y);	//RIGHT
        if(currentDirect==2) move(_x - _speed, _y);	//LEFT
        if(currentDirect==3) move(_x, _y - _speed);	//UP
	}
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;
		if(!canMove(xa, ya)) {
			_moving = true;
			return;
		}
        if(_y < ya) _direction = 2;
        if(_y > ya) _direction = 0;
        if(_x > xa) _direction = 3;
        if(_x < xa) _direction = 1;
		_y = ya;
		_x = xa;
	}
	
	@Override
	public boolean canMove(double x, double y) {
		// TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        double loLy = y-1;
        double loRy = y-1;
        double upLy = y - Game.TILES_SIZE ;
        double upRy = y - Game.TILES_SIZE ;
        double upLx = x;
        double loLx = x;
        double upRx = x-1 + Game.TILES_SIZE;
        double loRx = x-1 + Game.TILES_SIZE;
        int tile_UpLx = Coordinates.pixelToTile(upLx);
        int tile_UpLy = Coordinates.pixelToTile(upLy);
        int tile_UpRx = Coordinates.pixelToTile(upRx);
        int tile_UpRy = Coordinates.pixelToTile(upRy);
        int tile_LoLx = Coordinates.pixelToTile(loLx);
        int tile_LoLy = Coordinates.pixelToTile(loLy);
        int tile_LoRx = Coordinates.pixelToTile(loRx);
        int tile_LoRy = Coordinates.pixelToTile(loRy);
        Entity entity_UpLeft = _board.getEntity(tile_UpLx, tile_UpLy, this);
        Entity entity_UpRight = _board.getEntity(tile_UpRx, tile_UpRy, this);
        Entity entity_LoLeft = _board.getEntity(tile_LoLx, tile_LoLy, this);
        Entity entity_LoRight = _board.getEntity(tile_LoRx, tile_LoRy, this);
        if(entity_LoLeft instanceof Wall || entity_LoRight instanceof  Wall || entity_UpLeft instanceof Wall || entity_UpRight instanceof Wall) {
            return false;
        } else if(entity_LoLeft instanceof LayeredEntity || entity_LoRight instanceof  LayeredEntity || entity_UpLeft instanceof LayeredEntity || entity_UpRight instanceof LayeredEntity) {
			if (entity_LoLeft instanceof LayeredEntity) {
				Entity top = ((LayeredEntity) entity_LoLeft).getTopEntity();
				if (top instanceof Brick)
					return false;
			}
			if (entity_LoRight instanceof LayeredEntity) {
				Entity top = ((LayeredEntity) entity_LoRight).getTopEntity();
				if (top instanceof Brick)
					return false;
			}
			if (entity_UpLeft instanceof LayeredEntity) {
				Entity top = ((LayeredEntity) entity_UpLeft).getTopEntity();
				if (top instanceof Brick)
					return false;
			}
			if (entity_UpRight instanceof LayeredEntity) {
				Entity top = ((LayeredEntity) entity_UpRight).getTopEntity();
				if (top instanceof Brick)
					return false;
			}
        }
        if(collide(entity_LoLeft)||collide(entity_LoRight)||collide(entity_UpLeft)||collide(entity_UpRight))
            return false;
        return true;
	}

	@Override
	public boolean collide(Entity e) {
		if(e instanceof Bomb) {
            return true;
        }
		// TODO: xử lý va chạm với Flame
		if(e instanceof FlameSegment)
			e.collide(this);

		if(e instanceof Bomber) {
			if(e.getXTile()==getXTile()&&e.getYTile()==getYTile())
				((Bomber) e).kill();
		}
		// TODO: xử lý va chạm với Bomber
		return false;
	}
	
	@Override
	public void kill() {
		if(!_alive) return;

		_alive = false;
		Thread t = new Thread(new Sound(Action.enemyDied, false));
		t.start();
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();
}
