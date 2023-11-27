package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

/**
 * The Entity class is an abstract class.
 */
public abstract class Entity {

    protected static ArrayList<Entity> entities = new ArrayList<>();
    protected Vector position;
    protected Canvas canvas;
    protected GraphicsContext graphics;
    protected Destructible destructible;
    protected boolean collision;

    /**
     *  The `public Entity(Canvas canva, Vector position, Destructible destructible)` constructor is initializing an instance of the `Entity` class. It takes three parameters: `canva` (a `Canvas` object), `position` (a `Vector` object), and `destructible` (a `Destructible` object).
     */
    public Entity(Canvas canva, Vector position, Destructible destructible) {
        this.canvas = canva;
        this.position = position;
        this.destructible = destructible;
        entities.add(this);
    }

    /**
     * The function returns the position vector.
     * 
     * @return The method is returning a Vector object.
     */
    public Vector getPosition() {
        return position;
    }

    /**
     * The function sets the position of an object using a given vector.
     * 
     * @param position The "position" parameter is a Vector object that represents the position of an object in a 2D or 3D space.
     */
    public void setPosition(Vector position) {
        this.position = position;
    }

    /**
     * The function returns the canvas object.
     * 
     * @return The method is returning a Canvas object.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * The function sets the canvas for the object.
     * 
     * @param canvas The "canvas" parameter is an object of the "Canvas" class.
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

   /**
    * The function returns the GraphicsContext object.
    * 
    * @return The method is returning a GraphicsContext object.
    */
    public GraphicsContext getGraphics() {
        return graphics;
    }

    public void setGraphics(GraphicsContext graphics) {
        this.graphics = graphics;
    }

    /**
     * The function "paint" is an abstract method that does not have an implementation and must be overridden by any class that extends the abstract class containing this method.
     */
    public abstract void paint();

    /**
     * The function removes an entity from a list of entities if it is found.
     * 
     * @param entity The "entity" parameter is an object of type "Entity" that represents the entity to be removed from the list of entities.
     */
    public void removeEntity(Entity entity) {
        boolean found = false;
        for (int i = 0; i < entities.size() && !found; i++) {
            if (entities.get(i).equals(entity)) {
                found = true;
                entities.remove(i);
            }
        }
    }

    /**
     * The function returns a Destructible object.
     * 
     * @return The method is returning an object of type Destructible.
     */
    public Destructible getDestructible() {
        return destructible;
    }

   /**
    * The function sets the value of the static variable "entities" in the Entity class to the given ArrayList of Entity objects.
    * 
    * @param entities1 An ArrayList of Entity objects.
    */
    public static void setEntities(ArrayList<Entity> entities1) {
        Entity.entities = entities1;
    }

}
