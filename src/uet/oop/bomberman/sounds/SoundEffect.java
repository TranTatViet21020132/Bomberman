package uet.oop.bomberman.sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class SoundEffect {
    private Clip clip;
    public float previousVolume = -80;
    public float currentVolume = 6;
    private FloatControl fc;
    private boolean mute = false;
    private int clipPause = 0;

    public void setFile(String soundFileName) {
        try {
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception e) {

        }
    }
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void pause() {
        clipPause = clip.getFramePosition();
        clip.stop();
    }

    public void continueClip() {
        clip.setFramePosition(clipPause);
        clip.start();
    }

    public void volumeUp() {
        currentVolume += 1.0f;
        if (currentVolume > 6.0f) {
            currentVolume = 6.0f;
        }
        fc.setValue(currentVolume);
    }

    public void volumeDown() {
        currentVolume -= 1.0f;
        if (currentVolume < -80.0f) {
            currentVolume = -80.0f;
        }
        fc.setValue(currentVolume);
    }

    public void mute() {
        if (!mute) {
            previousVolume = currentVolume;
            currentVolume = -80.0f;
            fc.setValue(currentVolume);
            mute = true;
        } else {
            currentVolume = previousVolume;
            fc.setValue(currentVolume);
            mute = false;
        }
    }

    public FloatControl getFc() {
        return fc;
    }
}
