package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;

public abstract class Avatar  extends Entity{
    public Avatar(Canvas canva, Vector position, Destructible destructible) {
        super(canva, position, destructible);
    }


    public abstract void paint();
}
