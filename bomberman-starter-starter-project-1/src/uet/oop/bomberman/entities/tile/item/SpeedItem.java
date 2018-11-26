package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.Sound.Action;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item
{

    public SpeedItem(int x, int y, Sprite sprite)
    {
        super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e)
    {
        // TODO: xử lý Bomber ăn Item
        if (e instanceof Bomber)
        {
            int xBomber = e.getXTile();
            int yBomber = e.getYTile();
            if (getX() == xBomber && getY() == yBomber)
            {
                Thread t = new Thread(new Sound(Action.itemGet, false));
                t.start();
                remove();
                Game.addBomberSpeed(1);
            }
        }
        return false;
    }
}
