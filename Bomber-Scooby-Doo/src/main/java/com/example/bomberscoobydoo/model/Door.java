package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The Door class is a subclass of the Entity class.
 */
public class Door extends Entity {

    private Image doorImage;

    /**
     *  The `public Door(Canvas canvas, Vector position)` constructor is creating a new instance of the `Door` class. It takes two parameters: `canvas` of type `Canvas` and `position` of type `Vector`.
     */
    public Door(Canvas canvas, Vector position) {
        super(canvas, position, Destructible.INDESTRUCTIBLE);
        doorImage = new Image(getClass().getResourceAsStream("/images/Banner/door.png"), 60, 60, false, false);
    }

    /**
     * The paint function draws an image of a door on a canvas at a specified position.
     */
    public void paint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(doorImage, position.getX(), position.getY());
    }

}