package com.example.bomberscoobydoo.effects;


/**
 * The AudioManager class is used for managing audio in a Java program.
 */
public class AudioManager {

    private Thread soundThread;
    private Audio music;
    private static AudioManager instance;

    private AudioManager() {
    }

    /**
     * The function returns an instance of the AudioManager class, creating a new instance if one does not already exist.
     * 
     * @return The method is returning an instance of the AudioManager class.
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * The playEffect function creates a new thread to play an audio effect specified by the given path.
     * 
     * @param path The path parameter is a string that represents the file path or URL of the audio file that you want to play as an effect.
     */
    public void playEffect(String path) {
         new Thread( new Audio(path,AudioType.EFFECTS)).start();
    }

    /**
     * The function "playMusic" plays a music file located at the specified path.
     * @param path The path parameter is a string that represents the file path of the music file that you want to play.
     */
    public void playMusic(String path) {
        stopMusic();
        music=new Audio(path,AudioType.MUSIC);
        soundThread = new Thread(music);
        soundThread.start();
    }

    /**
     * The function stops the music and interrupts the sound thread if they are not null.
     */
    public void stopMusic() {
        if (music != null) {
            music.stop();
        }
        if(soundThread != null)
            soundThread.interrupt();
    }

}
