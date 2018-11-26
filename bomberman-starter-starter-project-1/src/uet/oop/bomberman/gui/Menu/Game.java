package uet.oop.bomberman.gui.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import uet.oop.bomberman.gui.Frame;


public class Game extends JMenu
{
    public Frame frame;

    public Game(Frame frame)
    {
        super("Game");
        this.frame = frame;
        JMenuItem newgame = new JMenuItem("NEW GAME");
        newgame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newgame.addActionListener(new MenuActionListener(frame));
        add(newgame);

        JMenuItem restart = new JMenuItem("RESTART");
        restart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.SHIFT_MASK));
        restart.addActionListener(new MenuActionListener(frame));
        add(restart);

        JMenuItem pause = new JMenuItem("PAUSE");
        pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        pause.addActionListener(new MenuActionListener(frame));
        add(pause);

        JMenuItem resume = new JMenuItem("RESUME");
        resume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        resume.addActionListener(new MenuActionListener(frame));
        add(resume);

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
            if (e.getActionCommand().equals("NEW GAME"))
            {
                _frame.newGame();
            }
            if (e.getActionCommand().equals("RESTART"))
            {
                _frame.restart();
            }
            if (e.getActionCommand().equals("PAUSE"))
            {
                _frame.pause();
            }
            if (e.getActionCommand().equals("RESUME"))
            {
                _frame.resume();
            }
        }
    }
}
