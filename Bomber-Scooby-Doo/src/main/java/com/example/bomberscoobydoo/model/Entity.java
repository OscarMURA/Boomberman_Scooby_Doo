package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {

    protected Vector position;
    protected Canvas canvas;
    protected GraphicsContext graphics;
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;

    public Entity(Canvas canva, Vector position){
        this.canvas = canva;
        this.position=position;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public GraphicsContext getGraphics() {
        return graphics;
    }

    public void setGraphics(GraphicsContext graphics) {
        this.graphics = graphics;
    }
    public abstract void paint();

    protected boolean collisionWithCanva(){

        if(position.getX() < 10 )
             left=true;
        else
            left=false;

        if(position.getX() > canvas.getWidth()-70)
            right=true;
        else
            right=false;

        if(position.getY() < 10)
            up=true;
        else
            up=false;

        if(position.getY() > canvas.getHeight()-70)
            down=true;
        else
            down=false;

        return false;
    }
}
