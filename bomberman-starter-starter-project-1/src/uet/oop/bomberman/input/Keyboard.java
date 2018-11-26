package uet.oop.bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Tiếp nhận và xử lý các sự kiện nhập từ bàn phím
 */
public class Keyboard implements KeyListener
{

    public boolean up, down, left, right, space, enter;
    private boolean[] keys = new boolean[120]; //120 is enough to this game

    public void update()
    {
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
        space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_X];
        enter = keys[KeyEvent.VK_ENTER];
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        try
        {
            keys[e.getKeyCode()] = true;
        } catch (ArrayIndexOutOfBoundsException aie)
        {

        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        try
        {
            keys[e.getKeyCode()] = false;
        } catch (ArrayIndexOutOfBoundsException aie)
        {

        }
    }
}
