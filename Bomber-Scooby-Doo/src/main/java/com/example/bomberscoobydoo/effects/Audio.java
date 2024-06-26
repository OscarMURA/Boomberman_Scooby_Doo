package com.example.bomberscoobydoo.effects;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * The Audio class implements the Runnable interface.
 */
public class Audio implements Runnable {

    private  File musicPath;
    private  Clip clip;
    private AudioType type;


    /** 
     * The `public Audio(String path, AudioType type)` constructor is responsible for initializing an instance of the `Audio` class.
     **/
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

    /**
     * The function plays a sound file located at the specified path, looping it indefinitely.
     */
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


    /**
     * The function plays an audio effect if the music file exists.
     */
    public void playEffect(){
        if(musicPath!=null &&musicPath.exists()) {

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

    /**
     * The function stops and closes the audio clip if it is not null.
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    /**
     * The run() function plays a sound based on the type of audio (music or effect).
     */
    @Override
    public void run() {
        if (type == AudioType.MUSIC) {
            playSound();
        } else {
            playEffect();
        }
    }
}
