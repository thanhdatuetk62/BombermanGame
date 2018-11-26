package uet.oop.bomberman.gui.Menu;

import javax.swing.*;
import uet.oop.bomberman.gui.Frame;

public class Menu extends JMenuBar
{
    public Menu(Frame frame)
    {
        add(new Game(frame));
        add(new Player(frame));
    }
}
