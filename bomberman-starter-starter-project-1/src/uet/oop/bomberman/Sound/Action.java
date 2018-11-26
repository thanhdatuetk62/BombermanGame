package uet.oop.bomberman.Sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Action
{
    /*
	|--------------------------------------------------------------------------
	| Character Actions
	|--------------------------------------------------------------------------
	 */
    public static final AudioClip bomberWalk = Applet.newAudioClip(Action.class.getResource("/Sounds/SHIBIRE.wav"));
    public static final AudioClip placeBomb = Applet.newAudioClip(Action.class.getResource("/Sounds/BOM_SET.wav"));
    public static final AudioClip enemyDied = Applet.newAudioClip(Action.class.getResource("/Sounds/FA118_11.wav"));
    public static final AudioClip bomberDied = Applet.newAudioClip(Action.class.getResource("/Sounds/GA128_11.wav"));
    /*
   |--------------------------------------------------------------------------
   | Entity effects
   |--------------------------------------------------------------------------
    */
    public static final AudioClip brickBreak = Applet.newAudioClip(Action.class.getResource("/Sounds/PRESS_BLOCK.wav"));
    public static final AudioClip portal = Applet.newAudioClip(Action.class.getResource("/Sounds/SW_11K.wav"));
    public static final AudioClip itemGet = Applet.newAudioClip(Action.class.getResource("/Sounds/ITEM_GET.wav"));
    public static final AudioClip explode = Applet.newAudioClip(Action.class.getResource("/Sounds/BOM_11_M.wav"));
    /*
   |--------------------------------------------------------------------------
   | Game Actions
   |--------------------------------------------------------------------------
    */
    public static final AudioClip pass = Applet.newAudioClip(Action.class.getResource("/Sounds/PAS_OK2.wav"));
    public static final AudioClip finishGame = Applet.newAudioClip(Action.class.getResource("/Sounds/FINISH.wav"));
    public static final AudioClip endGame = Applet.newAudioClip(Action.class.getResource("/Sounds/PLAYER_OUT.wav"));
}
