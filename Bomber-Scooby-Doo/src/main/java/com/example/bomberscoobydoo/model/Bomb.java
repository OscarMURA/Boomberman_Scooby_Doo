package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bomb extends Entity{

    private ImageView image;
    public Bomb(Canvas canva, Vector position){
        super(canva, position, Destructible.INDESTRUCTIBLE);
        Image tempImage = new Image(getClass().getResourceAsStream("/images/Banner/bombs.png"),60,60,false,false);
        image = new ImageView(tempImage);
    }

    @Override
    public void paint(){
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(image.getImage(), position.getX(),position.getY());
    }
}
