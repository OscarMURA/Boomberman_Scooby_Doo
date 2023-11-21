package com.example.bomberscoobydoo.effects;


public class AudioManager {

    private Thread soundThread;
    private Audio music;
    private static AudioManager instance;


    private AudioManager() {
    }


    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playEffect(String path) {
        Thread effectThread = new Thread( new Audio(path,AudioType.EFFECTS));
        effectThread.start();
    }

    public void playMusic(String path) {
        stopMusic();
        music=new Audio(path,AudioType.MUSIC);
        soundThread = new Thread(music);
        soundThread.start();
    }

    public void stopMusic() {
        if (music != null) {
            music.stop();
        }
        if(soundThread != null)
            soundThread.interrupt();
    }

}
