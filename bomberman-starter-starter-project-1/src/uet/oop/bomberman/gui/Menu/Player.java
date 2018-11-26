package uet.oop.bomberman.gui.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import uet.oop.bomberman.gui.Frame;

public class Player extends JMenu
{
    public Frame frame;

    public Player(Frame frame)
    {
        super("Player");
        this.frame = frame;
        JMenuItem auto = new JMenuItem("Auto");
        auto.addActionListener(new MenuActionListener(frame));
        add(auto);
        JMenuItem manual = new JMenuItem("Manual");
        manual.addActionListener(new MenuActionListener(frame));
        add(manual);
    }

    class MenuActionListener implements ActionListener
    {
        public Frame _frame;

        public MenuActionListener(Frame frame)
        {
            _frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("Auto"))
            {
                _frame.auto();
            }
            if (e.getActionCommand().equals("Manual"))
            {
                _frame.manual();
            }
        }
    }
}
