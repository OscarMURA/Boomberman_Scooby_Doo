package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Door extends Entity {

    private Image doorImage;

    public Door(Canvas canvas, Vector position) {
        super(canvas, position, Destructible.INDESTRUCTIBLE);
        doorImage = new Image(getClass().getResourceAsStream("/images/Banner/door.png"), 60, 60, false, false);
    }

    public void paint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(doorImage, position.getX(), position.getY());
    }

}