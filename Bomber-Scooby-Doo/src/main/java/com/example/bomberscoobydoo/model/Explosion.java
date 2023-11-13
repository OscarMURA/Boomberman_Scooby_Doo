package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Explosion extends Entity {

    private ArrayList<Image> frames;
    private int intensity;

    private long startTime;

    public Explosion(Canvas canva, Vector position, int intensity) {
        super(canva, position, Destructible.DESTRUCTIBLE);
        frames = new ArrayList<>();
        this.intensity = intensity;

        for (int i = 1; i <= 3; i++) {
            Image image = new Image(getClass().getResourceAsStream("/images/Banner/exp"+i+".png"),55,55,false,false);
            //Image image = new Image(getClass().getResourceAsStream("/images/player/SCOOBYDOO/run/run-"+i+".png"),60,60,false,false);
            frames.add(image);
        }

        startTime = System.currentTimeMillis();
    }

    public void paint() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        if (System.currentTimeMillis() - startTime < 250) {
            graphics.drawImage(frames.get(0), position.getX(), position.getY());
        } else if (System.currentTimeMillis() - startTime < 500) {
            graphics.drawImage(frames.get(1), position.getX(), position.getY());
        } else if (System.currentTimeMillis() - startTime < 750) {
            graphics.drawImage(frames.get(2), position.getX(), position.getY());
        }

    }

    public long getStartTime() {
        return startTime;
    }
}
