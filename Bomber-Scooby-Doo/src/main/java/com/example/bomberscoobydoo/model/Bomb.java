package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Bomb extends Entity{

    private boolean playerOutSideBomb;
    private ArrayList<ImageView> images;
    private long startTime;

    public Bomb(Canvas canva, Vector position, int intensity){
        super(canva, position, Destructible.INDESTRUCTIBLE);
        //initImages();
        playerOutSideBomb = false;
        startTime = System.currentTimeMillis();
        initImages();
    }

    public boolean isPlayerOutSideBomb() {
        return playerOutSideBomb;
    }


    public void checkIfPlayerOutSideBomb(int x, int y) {
        int epsilon = 50;
        if(!playerOutSideBomb)
            playerOutSideBomb = (x + epsilon < position.getX() ||
                    x > position.getX() + epsilon ||
                    y + epsilon < position.getY() ||
                    y > position.getY() + epsilon);
    }

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


    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void initImages(){
        images = new ArrayList<>();
        for(int i = 1; i <= 4; i++){
            System.out.println(i);
            images.add(new ImageView(new Image(getClass().getResourceAsStream("/images/Banner/Boom-"+i+".png"),50,50,false,false)));
        }
    }


}