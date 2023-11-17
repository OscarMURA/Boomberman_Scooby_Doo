package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Bricks extends Entity {

    private Image box;

    public Bricks(Canvas canva, Vector position) {
        super(canva, position, Destructible.DESTRUCTIBLE);
        Random r = new Random();
        int i = r.nextInt(3) + 1;
        box = new Image(getClass().getResourceAsStream("/blocks/bricks/box" + i + ".png"), 60, 60, false, false);
    }

    @Override
    public void paint() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(box, position.getX(), position.getY());
    }

}
