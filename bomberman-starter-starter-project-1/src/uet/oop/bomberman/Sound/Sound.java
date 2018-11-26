package uet.oop.bomberman.Sound;

import java.applet.AudioClip;

public class Sound implements Runnable
{
    private AudioClip sound;
    private boolean isLoop;
    private boolean isStop;

    public Sound(AudioClip sound, boolean l)
    {
        this.sound = sound;
        this.isLoop = l;
    }

    @Override
    public void run()
    {
        try
        {
            if (!isStop)
            {
                if (isLoop)
                {
                    sound.loop();
                } else sound.play();
                Thread.sleep(500);
            } else
            {
                sound.stop();
            }
        } catch (InterruptedException e)
        {

        }
    }

    public void stop()
    {
        isStop = true;
    }

    public void resume()
    {
        isStop = false;
        run();
    }

    public boolean isStopped()
    {
        return isStop;
    }
}
