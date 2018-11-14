package uet.oop.bomberman.Sound;

import javax.sound.sampled.*;
import java.applet.AudioClip;
import java.io.IOException;

public class Sound implements Runnable{
    private AudioClip sound;
    private boolean isLoop;
    public Sound(AudioClip sound, boolean l) {
        this.sound = sound;
        this.isLoop = l;
    }

    @Override
    public void run() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource("/Sounds/PLAYER_WALK.wav"));
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            sound.play();
            Thread.sleep(500);

        } catch (UnsupportedAudioFileException e) {

        } catch (IOException e){

        } catch (InterruptedException e) {

        }
    }
}
