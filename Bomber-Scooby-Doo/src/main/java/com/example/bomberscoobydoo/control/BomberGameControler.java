package com.example.bomberscoobydoo.control;

import com.example.bomberscoobydoo.model.Player;
import com.example.bomberscoobydoo.model.PlayerType;

public class BomberGameControler {

    private Player player;
    private int level = 0;
    private long time = System.currentTimeMillis();
    private static BomberGameControler instance;

    /**
     * The function returns an instance of the BomberGameControler class, creating a new instance if one does not already exist.
     * 
     * @return The method is returning an instance of the BomberGameControler class.
     */
    public static BomberGameControler getInstance() {
        if (instance == null) {
            instance = new BomberGameControler();
        }
        return instance;
    }

    private BomberGameControler() {
    }

    /**
     * The function creates a player object with a given name and type, either "Scooby Doo" or "Shaggy".
     * 
     * @param name The name of the player that you want to create.
     * @param type The "type" parameter is a String that represents the type of player. It can be either "Scooby Doo" or any other value.
     * @return The method is returning a boolean value of true.
     */
    public boolean createPlayer(String name, String type) {
        if (type.equals("Scooby Doo"))
            player = new Player(name, PlayerType.SCOOBYDOO);
        else
            player = new Player(name, PlayerType.SHAGGY);
        return true;
    }

    /**
     * The function returns the player object.
     * 
     * @return The method is returning an object of type Player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * The function returns the value of the level variable.
     * 
     * @return The method is returning the value of the variable "level".
     */
    public int getLevel() {
        return level;
    }

    /**
     * The function increments the level variable by 1 and updates the time variable with the current system time.
     */
    public void nextLevel() {
        level++;
        time = System.currentTimeMillis();
    }

   /**
    * The getTime() function returns the value of the time variable.
    * 
    * @return The method is returning a long value, specifically the value of the variable "time".
    */
    public long getTime() {
        return time;
    }

    /**
     * The setTime function sets the value of the time variable.
     * 
     * @param time The "time" parameter is a long data type, which represents a specific point in time measured in milliseconds.
     */
    public void setTime(long time) {
        this.time = time;
    }


}
