package com.example.bomberscoobydoo.effects;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Audio implements Runnable {

    private File musicPath;
    private Clip clip;
    private AudioType type;

    public Audio(String path, AudioType type) {
        URL url = getClass().getResource("/music" + path);
        if (url != null) {
            try {
                musicPath = new File(url.toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.type = type;
    }

    private void playSound() {
        if (musicPath.exists()) {
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(musicPath));
                clip.start();
                clip.stop();
                clip.loop(1000);

            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("No existe el archivo");
    }

    public void playEffect() {
        if (musicPath.exists()) {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(musicPath));
                clip.start();
                clip.stop();
                clip.loop(0);

            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    @Override
    public void run() {
        if (type == AudioType.MUSIC) {
            playSound();
        } else {
            playEffect();
        }
    }
}
