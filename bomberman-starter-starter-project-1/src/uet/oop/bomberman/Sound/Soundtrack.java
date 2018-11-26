package uet.oop.bomberman.Sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Soundtrack implements Runnable
{
    public static final AudioClip ground = Applet.newAudioClip(Action.class.getResource("/Sounds/MAIN.wav"));

    public void run()
    {
        ground.play();
    }
}
