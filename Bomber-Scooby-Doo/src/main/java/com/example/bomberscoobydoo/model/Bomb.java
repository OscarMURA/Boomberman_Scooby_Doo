package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.example.bomberscoobydoo.effects.AudioManager;
import java.util.ArrayList;

public class Bomb extends Entity{

    private boolean playerOutSideBomb;
    private ArrayList<ImageView> images;
    private long startTime;
    /**
     * The code snippet is the constructor of the `Bomb` class. It initializes a `Bomb` object with the given parameters `canva`, `position`, and `intensity`.
     */
    public Bomb(Canvas canva, Vector position, int intensity){
        super(canva, position, Destructible.INDESTRUCTIBLE);
        playerOutSideBomb = false;
        startTime = System.currentTimeMillis();
        initImages();
        AudioManager.getInstance().playEffect("/pop.waw");
    }

    /**
     * The function returns a boolean value indicating whether the player is outside the bomb.
     * 
     * @return The method is returning a boolean value, specifically the value of the variable "playerOutSideBomb".
     */
    public boolean isPlayerOutSideBomb() {
        return playerOutSideBomb;
    }

    /**
     * The function checks if the player is outside a bomb based on their coordinates.
     * 
     * @param x The x-coordinate of the player's position.
     * @param y The y parameter represents the y-coordinate of the player's position.
     */
    public void checkIfPlayerOutSideBomb(int x, int y) {
        int epsilon = 50;
        if(!playerOutSideBomb)
            playerOutSideBomb = (x + epsilon < position.getX() ||
                    x > position.getX() + epsilon ||
                    y + epsilon < position.getY() ||
                    y > position.getY() + epsilon);
    }

   /**
    * The paint() function uses the GraphicsContext class to draw different images on a canvas based on the current time.
    */
    @Override
    public void paint(){
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        if(System.currentTimeMillis() - startTime < 600)
            graphics.drawImage(images.get(0).getImage(), position.getX(),position.getY());
        else if(System.currentTimeMillis() - startTime < 1200)
            graphics.drawImage(images.get(1).getImage(), position.getX(),position.getY());
        else if(System.currentTimeMillis() - startTime < 1800)
            graphics.drawImage(images.get(2).getImage(), position.getX(),position.getY());
        else if(System.currentTimeMillis() - startTime < 2000)
            graphics.drawImage(images.get(3).getImage(), position.getX(),position.getY());

    }

    /**
     * The function returns the start time.
     * 
     * @return The method is returning the value of the variable "startTime" which is of type long.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * The function sets the start time for a specific object.
     * 
     * @param startTime The startTime parameter is a long value that represents the starting time of an event or process.

     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * The function initializes an ArrayList of ImageView objects with images from a specific directory.
     */
    public void initImages(){
        images = new ArrayList<>();
        for(int i = 1; i <= 4; i++){
            images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/Banner/Boom-"+i+".png"),50,50,false,false)));
        }
    }


}