package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.ai.AIHigh;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Doria extends Enemy
{
    public Doria(int x, int y, Board board)
    {
        super(x, y, board, Sprite.kondoria_dead, Game.getBomberSpeed() / 4, 1000);

        _sprite = Sprite.kondoria_left1;
        _ai = new AIHigh(board, this);
        //_direction = _ai.calculateDirection();
    }

    @Override
    public boolean canMove(double x, double y)
    {
        double loLy = y - 1;
        double loRy = y - 1;
        double upLy = y - Game.TILES_SIZE;
        double upRy = y - Game.TILES_SIZE;
        double upLx = x;
        double loLx = x;
        double upRx = x - 1 + Game.TILES_SIZE;
        double loRx = x - 1 + Game.TILES_SIZE;
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
        if (entity_LoLeft instanceof Wall || entity_LoRight instanceof Wall || entity_UpLeft instanceof Wall || entity_UpRight instanceof Wall)
        {
            return false;
        }
        if (collide(entity_LoLeft) || collide(entity_LoRight) || collide(entity_UpLeft) || collide(entity_UpRight))
            return false;
        return true;
    }

    @Override
    protected void chooseSprite()
    {
        //_direction = _ai.calculateDirection();
        switch (_direction)
        {
            case 0:
            case 1:
                _sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
                break;
            case 2:
            case 3:
                _sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
                break;
        }
    }
}
