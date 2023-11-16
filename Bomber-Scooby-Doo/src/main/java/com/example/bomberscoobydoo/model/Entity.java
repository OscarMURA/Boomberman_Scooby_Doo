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
    protected boolean leftCollision;
    protected boolean rightCollision;
    protected boolean upCollision;
    protected boolean downCollision;
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


    protected boolean collidesWithOtherEntity(Entity other, int x, int y) {
        collision = false;
        if(other instanceof Bomb){
            ((Bomb) other).checkIfPlayerOutSideBomb(x, y);
        }
        if (this != other && !(other instanceof Explosion) &&
                (!(other instanceof Bomb) || (other instanceof Bomb && ((Bomb)other).isPlayerOutSideBomb())
                ) ) {
            int epsilon = 45;
            if(this instanceof Enemy){
                epsilon = 40;
            }
            collision = !(x + epsilon < other.position.getX() ||
                    x > other.position.getX() + epsilon ||
                    y + epsilon < other.position.getY() -10 ||
                    y > other.position.getY() + epsilon);
            if(this instanceof Player && other instanceof Enemy && collision){
                ((Player)this).setLife(((Player)this).getLife()-1);
                Vector vector = new Vector(0,0);
                ((Player)this).setPosition(vector);
            }
        }
        return collision;
    }

    protected boolean checkCollisions(int x,int y) {
        for (Entity entity : entities) {
            if (this != entity && (collidesWithOtherEntity(entity,x,y))){
                return false;
            }
        }
        return true;
    }

    protected void moveDirection(double directionX, double direction){
        if(!(directionX==0 && direction==0)){
            double x = position.getX() + directionX;
            double y = position.getY() + direction;
            if(checkCollisions((int)x,(int)y) ){
                position.setX(x);
                position.setY(y);
            }
        }
    }

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
