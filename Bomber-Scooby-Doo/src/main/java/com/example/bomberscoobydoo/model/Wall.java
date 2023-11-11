package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Wall extends Entity {

    private Image iron;

    public Wall(Canvas canva, Vector position) {
        super(canva, position, Destructible.INDESTRUCTIBLE);
        Random r=new Random();
        int i=r.nextInt(2)+1;
        iron=new Image(getClass().getResourceAsStream("/blocks/wall/iron"+i+".png"),60,60,false,false);
    }
    @Override
    public void paint() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(iron,position.getX(),position.getY());
    }
}
