package com.example.bomberscoobydoo.screens;

import com.example.bomberscoobydoo.control.BomberGameControler;
import com.example.bomberscoobydoo.effects.AudioManager;
import com.example.bomberscoobydoo.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

/**
 * The BaseScreen class is an abstract class.
 */
public abstract class BaseScreen {
    protected long startTime;
    protected Image imageLevel;
    protected boolean level;
    protected static Player player;
    protected ArrayList<Entity> entities;
    protected Image background;
    protected Canvas canvas;
    protected GraphicsContext graphicsContext;
    protected AudioManager audio = AudioManager.getInstance();

    // The `BaseScreen` constructor initializes the `startTime` variable with the
    // current system time,
    // sets the `level` variable to `true`, and assigns the `canvas` parameter to
    // the `canvas` instance
    // variable. It also initializes the `graphicsContext` variable with the 2D
    // graphics context of the
    // canvas.
    public BaseScreen(Canvas canvas) {
        startTime = System.currentTimeMillis();
        level = true;
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        player = BomberGameControler.getInstance().getPlayer();
        this.entities = new ArrayList<>();
    }

    /**
     * The function "paint" is declared as an abstract method, meaning it does not
     * have an implementation
     * and must be overridden by any concrete subclass.
     */
    public abstract void paint();

    /**
     * The function puts a bomb at a specific location if there is no entity already
     * present at that
     * location.
     */
    public void putBomb() {
        boolean somethingAlreadyThere;
        Vector ubicationOfBomb = player.getPosition().clone();
        ubicationOfBomb.setX(ubicationOfBomb.getX() + 30);
        ubicationOfBomb.setY(ubicationOfBomb.getY() + 30);
        ubicationOfBomb = ubicationOfBomb.getTilePosition();
        somethingAlreadyThere = checkIfEntityIsThere(ubicationOfBomb);
        if (!somethingAlreadyThere) {
            ((Player) (player)).setAmountBombs(((Player) (player)).getAmountBombs() - 1);
            entities.add(new Bomb(canvas, ubicationOfBomb, ((Player) (player)).getIntensityOfExplosions()));
        }
    }

    /**
     * The function checks for expired bombs and explosions in a game and removes them from the list of
     * entities.
     */
    protected void checkExplosions() {
        ArrayList<Entity> toRemove = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof Bomb && System.currentTimeMillis() - ((Bomb) e).getStartTime() >= 2000) {
                toRemove.add(e);

            } else if (e instanceof Explosion && System.currentTimeMillis() - ((Explosion) e).getStartTime() >= 600) {
                toRemove.add(e);
            }
        }
        for (Entity e : toRemove) {
            entities.remove(e);
            e.removeEntity(e);
            if (e instanceof Bomb) {
                Vector position = e.getPosition().clone();
                int intensity = ((Player) (player)).getIntensityOfExplosions();
                explodeBomb(intensity, position, MoveType.STOP);
            }
            e = null;
        }

    }

    /**
     * The `explodeBomb` function creates explosions in a game based on the given intensity, position,
     * and direction, destroying entities in its path.
     * 
     * @param intensity The intensity parameter represents the power or strength of the bomb explosion.
     * It determines how far the explosion will reach and how much damage it will cause.
     * @param position The position parameter represents the current position of the bomb. It is of
     * type Vector, which likely contains the x and y coordinates of the position.
     * @param direction The `direction` parameter is of type `MoveType` and represents the direction in
     * which the explosion is propagating. It can have one of the following values:
     */
    private void explodeBomb(int intensity, Vector position, MoveType direction) {
        if (intensity >= 0) {
            if (!checkIfEntityIsThere(position)) {
                entities.add(new Explosion(canvas, position.clone(), intensity));
                if (intensity > 0) {
                    switch (direction) {
                        case STOP -> {
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.LEFT), MoveType.LEFT);
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.RIGHT), MoveType.RIGHT);
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.UP), MoveType.UP);
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.DOWN), MoveType.DOWN);
                        }
                        case LEFT ->
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.LEFT), MoveType.LEFT);
                        case RIGHT ->
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.RIGHT), MoveType.RIGHT);
                        case UP -> explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.UP), MoveType.UP);
                        case DOWN ->
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.DOWN), MoveType.DOWN);
                    }
                }
            } else {
                boolean foundBox = false;
                Entity e = null;
                for (int i = 0; i < entities.size() && !foundBox; i++) {
                    e = entities.get(i);
                    if (position.equals(e.getPosition())) {
                        foundBox = true;
                    }
                }
                if (e != null && e.getDestructible() == Destructible.DESTRUCTIBLE) {
                    entities.add(new Explosion(canvas, e.getPosition(), intensity));
                    eliminateEntity(e);
                }
            }
        }
    }

    /**
     * The function eliminates an entity by removing it from a list and setting it to null.
     * 
     * @param e The parameter "e" is an instance of the Entity class that we want to eliminate.
     */
    public void eliminateEntity(Entity e) {
        entities.remove(e);
        e.removeEntity(e);
        e = null;
    }

    /**
     * The function checks if there is an entity at a given position in a list of entities.
     * 
     * @param position The position parameter is a Vector object that represents the position of an
     * entity.
     * @return The method is returning a boolean value, which indicates whether there is an entity at
     * the specified position.
     */
    private boolean checkIfEntityIsThere(Vector position) {
        boolean someThingAlreadyThere = false;
        for (int i = 0; i < entities.size() && !someThingAlreadyThere; i++) {
            Entity e = entities.get(i);
            if (e.getPosition().equals(position)) {
                someThingAlreadyThere = true;
            }
        }
        return someThingAlreadyThere;
    }

    /**
     * The function checks if any enemy entities are overlapped by an explosion entity and removes them
     * if they are.
     */
    protected void checkEnemyOverExplosion() {
        ArrayList<Entity> toRemove = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                for (Entity e2 : entities) {
                    Vector position = e.getPosition().clone();
                    position.setX(position.getX() + 20);
                    position.setY(position.getY() + 20);
                    if (e2 instanceof Explosion && e2.getPosition().equals(position.getTilePosition())) {
                        toRemove.add(e);
                    }
                }
            }
        }
        for (Entity e : toRemove) {
            eliminateEntity(e);
        }

    }

   /**
    * The function checks if the player is overlapping with an explosion entity and reduces the
    * player's life if they are.
    */
    protected void checkPlayerOverExplosion() {

        boolean someThingAlreadyThere = false;
        Vector edge1 = player.getPosition().clone();
        Vector edge2 = player.getPosition().clone();
        edge2.setX(edge2.getX() + 54);
        edge2.setY(edge2.getY() + 54);
        Vector edge3 = player.getPosition().clone();
        edge3.setX(edge3.getX() + 54);
        Vector edge4 = player.getPosition().clone();
        edge4.setY(edge4.getY() + 54);

        for (int i = 0; i < entities.size() && !someThingAlreadyThere; i++) {
            Entity e = entities.get(i);
            if (e instanceof Explosion &&
                    System.currentTimeMillis() - ( (player)).getInvensibilityStartTime() > 600 &&
                    (e.getPosition().equals(edge1.getTilePosition()) ||
                            e.getPosition().equals(edge2.getTilePosition()) ||
                            e.getPosition().equals(edge3.getTilePosition()) ||
                            e.getPosition().equals(edge4.getTilePosition()))) {

                someThingAlreadyThere = true;
                ( (player)).lowerByOneLife();
                ( (player)).setInvensibilityStartTime();
                entities.remove(e);
                e.removeEntity(e);
            }
        }
    }

    /**
     * The function takes a position and a direction as input and returns a new position that is 60
     * units away in the specified direction.
     * 
     * @param position The position parameter is a Vector object that represents the current position
     * of an object in a 2D space.
     * @param direction The direction parameter is of type MoveType, which is an enum representing the
     * direction in which to move. The possible values for direction are UP, DOWN, LEFT, and RIGHT.
     * @return The method is returning a Vector object.
     */
    private Vector getFollowingBlock(Vector position, MoveType direction) {
        Vector newPosition = position.clone();
        switch (direction) {
            case UP -> newPosition.setY(newPosition.getY() - 60);
            case DOWN -> newPosition.setY(newPosition.getY() + 60);
            case LEFT -> newPosition.setX(newPosition.getX() - 60);
            case RIGHT -> newPosition.setX(newPosition.getX() + 60);
        }
        return newPosition;
    }

    /**
     * The onKeyPressed function calls the onKeyPressed method of the Player class, passing in the
     * KeyEvent event as a parameter.
     * 
     * @param event The parameter "event" is of type KeyEvent, which represents a key press event.
     */
    public void onKeyPressed(KeyEvent event) {
        ((Player) player).onKeyPressed(event);
    }

    /**
     * The onKeyReleased function calls the onKeyReleased method of the Player class, passing in the
     * KeyEvent event as a parameter.
     * 
     * @param event The parameter "event" is of type KeyEvent, which represents a key release event.
     */
    public void onKeyReleased(KeyEvent event) {
        ((Player) player).onKeyReleased(event);
    }

}
