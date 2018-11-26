package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Sound.Action;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile
{

    public Portal(int x, int y, Sprite sprite)
    {
        super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e)
    {
        // TODO: xử lý khi Bomber đi vào
        if (e instanceof Bomber)
        {
            int xBomber = e.getXTile();
            int yBomber = e.getYTile();
            if (getX() == xBomber && getY() == yBomber)
            {
                Thread t = new Thread(new Sound(Action.portal, false));
                t.start();
                return true;
            }
        }
        return false;
    }

}
