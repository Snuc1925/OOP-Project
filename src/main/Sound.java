package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundEffectURL = new URL[30];
    URL[] themeURL = new URL[30];

    public float themeVolume = 0.1f, soundEffectVolume = 0.1f;

    public Sound() {
        themeURL[0] = getClass().getResource("/sound/Soundtrack/Snowland.wav");
        themeURL[1] = getClass().getResource("/sound/Theme/Demon.wav");
        themeURL[2] = getClass().getResource("/sound/Theme/BringerOfDeath.wav");
        themeURL[3] = getClass().getResource("/sound/Theme/Samurai.wav");

        soundEffectURL[0] = getClass().getResource("/sound/Demon/Explosion.wav");
        soundEffectURL[1] = getClass().getResource("/sound/Demon/FireBreath.wav");
        soundEffectURL[2] = getClass().getResource("/sound/Demon/Slash.wav");
        soundEffectURL[3] = getClass().getResource("/sound/Demon/Transform.wav");

        soundEffectURL[4] = getClass().getResource("/sound/Player/GunAttack.wav");
        soundEffectURL[5] = getClass().getResource("/sound/Player/SpearAttack.wav");

        soundEffectURL[6] = getClass().getResource("/sound/Samurai/attack1.wav");
        soundEffectURL[7] = getClass().getResource("/sound/Samurai/attack2.wav");

        soundEffectURL[8] = getClass().getResource("/sound/SkeletonReaper/attack1.wav");
        soundEffectURL[9] = getClass().getResource("/sound/SkeletonReaper/cast.wav");


    }
    public void setSE(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundEffectURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            setVolume("SE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTheme(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(themeURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            setVolume("theme");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {
        clip.start();

    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
    public void setVolume(String type) {
        float volume = soundEffectVolume;
        if (type.equals("theme")) volume = themeVolume;
        if (type.equals("SE")) volume = soundEffectVolume;
        if (clip != null && clip.isOpen()) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    public void playMusic(int index) {
        System.out.println(index);
        if (clip != null && clip.isRunning()) {
            System.out.println("Stopping current theme");
            clip.stop();
        }
        setTheme(index);
        play();
        loop();
    }

    public void playSE(int index) {
        setSE(index);
        play();
    }

}
