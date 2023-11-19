package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


import java.util.ArrayList;

public abstract class Entity {

    protected static  ArrayList<Entity> entities = new ArrayList<>();
    protected Vector position;
    protected Canvas canvas;
    protected GraphicsContext graphics;
    protected Destructible destructible;
    protected boolean collision;


    public Entity(Canvas canva, Vector position, Destructible destructible) {
        this.canvas = canva;
        this.position = position;
        this.destructible = destructible;
        entities.add(this);
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

    public void removeEntity(Entity entity){
        boolean found = false;
        for(int i = 0; i < entities.size() && !found; i++){
            if(entities.get(i).equals(entity)){
                found = true;
                entities.remove(i);
            }
        }
    }

    public Destructible getDestructible(){
        return destructible;
    }

    public static void setEntities(ArrayList<Entity> entities1) {
        Entity.entities = entities1;
    }

}
