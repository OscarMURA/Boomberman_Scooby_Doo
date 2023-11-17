package com.example.bomberscoobydoo.effects;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AudioManager extends Thread{

    private Thread soundThread;
    private static AudioManager instance;
    private File musicPath;
    private Clip clip;


    private AudioManager() {
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void setMusicPath(String path) {
        File efectPath = new File("src/main/resources/music" + path);
        musicPath = efectPath;
    }

    public void playSound(long durationMillis) {
        if(musicPath.exists()) {
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(musicPath));
                clip.start();
                clip.stop();
                clip.loop((int )durationMillis);

            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException  e) {
                e.printStackTrace();
            }
        }else
            System.out.println("No existe el archivo");
    }

    public void playEffect(String path){
        File efectPath = new File("src/main/resources/music" + path);
        if(efectPath.exists()) {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(efectPath));
                clip.start();
                clip.stop();
                clip.loop(0);

            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException  e) {
                e.printStackTrace();
            }
        }
    }

    public void playEffectInBackground(String path) {
        Thread effectThread = new Thread(() -> playEffect(path));
        effectThread.start();
    }

    public void playMusic(long loop) {
        stopMusic();
        soundThread = new Thread(() -> playSound( loop ));
        soundThread.start();
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
        if(soundThread != null)
            soundThread.interrupt();
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }

}
