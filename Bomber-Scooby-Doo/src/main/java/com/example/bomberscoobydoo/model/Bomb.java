package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bomb extends Entity{

    private boolean playerOutSideBomb;

    private ImageView image;

    private int intensity;

    private long startTime;
    public Bomb(Canvas canva, Vector position, int intensity){
        super(canva, position, Destructible.INDESTRUCTIBLE);
        Image tempImage = new Image(getClass().getResourceAsStream("/images/Banner/bombs.png"),60,60,false,false);
        image = new ImageView(tempImage);
        playerOutSideBomb = false;
        startTime = System.currentTimeMillis();
        this.intensity = intensity;
    }

    public boolean isPlayerOutSideBomb() {
        return playerOutSideBomb;
    }

    public ImageView getImage() {
        return image;
    }

    public void checkIfPlayerOutSideBomb(int x, int y) {
        int epsilon = 40;
        if(!playerOutSideBomb)
        playerOutSideBomb = (x + epsilon < position.getX() ||
                x > position.getX() + epsilon ||
                y + epsilon < position.getY() ||
                y > position.getY() + epsilon);
    }

    @Override
    public void paint(){
        if(System.currentTimeMillis()-startTime>5000){
            explode();
        }
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(image.getImage(), position.getX(),position.getY());
    }

    public void explode(){
        //todo
    }

}
