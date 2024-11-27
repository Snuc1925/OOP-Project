package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundEffectURL = new URL[30];
    URL[] themeURL = new URL[30];

    Clip[] soundtrack = new Clip[4], effect = new Clip[10];


    public float volume = 0.8f;
    public int currentSoundtrackId;
    public boolean soundtrackMute = false, effectMute = false;
    private Clip getClip(String path) {
        URL url = getClass().getResource(path);
        AudioInputStream audio;

        try {
            assert url != null;
            audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Sound() {
        soundtrack[0] = getClip("/sound/Soundtrack/Snowland.wav");
        soundtrack[1] = getClip("/sound/Theme/Demon.wav");
        soundtrack[2] = getClip("/sound/Theme/BringerOfDeath.wav");
        soundtrack[3] = getClip("/sound/Theme/Samurai.wav");

        effect[0] = getClip("/sound/Demon/Explosion.wav");
        effect[1] = getClip("/sound/Demon/FireBreath.wav");
        effect[2] = getClip("/sound/Demon/Slash.wav");
        effect[3] = getClip("/sound/Demon/Transform.wav");

        effect[4] = getClip("/sound/Player/GunAttack.wav");
        effect[5] = getClip("/sound/Player/SpearAttack.wav");

        effect[6] = getClip("/sound/Samurai/attack2.wav");
        effect[7] = getClip("/sound/Samurai/attack2.wav");

        effect[8] = getClip("/sound/SkeletonReaper/attack1.wav");
        effect[9] = getClip("/sound/SkeletonReaper/cast.wav");
    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateSoundtrackVolume();
        updateEffectsVolume();
    }

    private void updateSoundtrackVolume() {
        FloatControl gainControl = (FloatControl) soundtrack[currentSoundtrackId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }
    private void updateEffectsVolume() {
        for (Clip c : effect) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
    public void toggleSongMute() {
        this.soundtrackMute = !soundtrackMute;
        for (Clip c : soundtrack) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(soundtrackMute);
        }
    }

    public void toggleEffectMute() {
        this.effectMute = !effectMute;
        for (Clip c : effect) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
    }

    public void stopSong() {
        if (soundtrack[currentSoundtrackId].isActive())
            soundtrack[currentSoundtrackId].stop();
    }
    public void playSE(int effectId) {
        effect[effectId].setMicrosecondPosition(0);
        effect[effectId].start();
    }

    public void playMusic(int soundtrackId) {
        stopSong();

        currentSoundtrackId = soundtrackId;
        updateSoundtrackVolume();
        soundtrack[soundtrackId].setMicrosecondPosition(0);
        soundtrack[soundtrackId].loop(Clip.LOOP_CONTINUOUSLY);
    }





    public void setSE(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundEffectURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setTheme(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(themeURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);
//            setVolume("theme");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
