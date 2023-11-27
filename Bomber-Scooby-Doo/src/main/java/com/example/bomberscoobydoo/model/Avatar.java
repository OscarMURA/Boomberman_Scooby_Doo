package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;

/**
 * The Avatar class is an abstract class that extends the Entity class, represents entities that moves.
 */
public abstract class Avatar extends Entity {

    protected boolean goUp;
    protected boolean goDown;
    protected boolean goLeft;
    protected boolean goRight;
    protected double steps;
    protected boolean isMoving;
    protected int frame;
    protected int speed;
    protected MoveType moveType;
    protected boolean leftCollision;
    protected boolean rightCollision;
    protected boolean upCollision;
    protected boolean downCollision;

// The code `public Avatar(Canvas canva, Vector position, Destructible destructible)` is a constructor
// for the `Avatar` class. It takes three parameters: `canva`, `position`, and `destructible`.
    public Avatar(Canvas canva, Vector position, Destructible destructible) {
        super(canva, position, destructible);
    }

    // The `onMove()` method is responsible for handling the movement of the Avatar. It checks the
    // direction in which the Avatar should move (up, down, left, or right) based on the boolean flags
    // `goUp`, `goDown`, `goLeft`, and `goRight`. It also checks for collisions with the canvas
    // boundaries using the `collisionWithCanva()` method.
    public void onMove() {
        collisionWithCanva();
        int directionX = 0;
        int directionY = 0;
        if (goUp && !upCollision) {
            directionY = -speed;
        }
        if (goDown && !downCollision) {
            directionY = speed;
        }
        if (goLeft && !leftCollision) {
            directionX = -speed;
        }
        if (goRight && !rightCollision) {
            directionX = speed;
        }
        moveDirection(directionX, directionY);
    }

    /**
     * The function checks if the position of an object collides with the boundaries of a canvas.
     * 
     * @return The method is always returning false.
     */
    protected boolean collisionWithCanva() {
        if (position.getX() < 10)
            leftCollision = true;
        else
            leftCollision = false;
        if (position.getX() > canvas.getWidth() - 65)
            rightCollision = true;
        else
            rightCollision = false;
        if (position.getY() < 10)
            upCollision = true;
        else
            upCollision = false;
        if (position.getY() > canvas.getHeight() - 70)
            downCollision = true;
        else
            downCollision = false;
        return false;
    }

    /**
     * The function checks if the current entity collides with another entity and performs specific
     * actions based on the types of entities involved.
     * 
     * @param other The "other" parameter is an instance of the Entity class, which represents another
     * entity in the game. It could be any subclass of Entity, such as Player, Enemy, Bomb, or
     * Explosion.
     * @param x The x-coordinate of the entity's position.
     * @param y The parameter "y" represents the y-coordinate of the current entity's position.
     * @return The method is returning a boolean value, which indicates whether there is a collision
     * with another entity.
     */
    protected boolean collidesWithOtherEntity(Entity other, int x, int y) {
        collision = false;
        if (other instanceof Bomb && this instanceof Player) {
            ((Bomb) other).checkIfPlayerOutSideBomb(x, y);
        }
        if (this != other && !(other instanceof Explosion) &&
                (!(other instanceof Bomb) || (other instanceof Bomb && ((Bomb) other).isPlayerOutSideBomb()))) {
            int epsilon = 45;
            if (this instanceof Enemy) {
                epsilon = 38;
            }
            collision = !(x + epsilon < other.position.getX() ||
                    x > other.position.getX() + epsilon ||
                    y + epsilon < other.position.getY() - 10 ||
                    y > other.position.getY() + epsilon);

            if(this instanceof Player && other instanceof Enemy && collision){
                ((Player)this).lowerByOneLife();
                Vector vector = new Vector(0,0);

                this.setPosition(vector);
            }

        }
        return collision;
    }

/**
 * The function checks if the current entity collides with any other entity at the given coordinates.
 * 
 * @param x The x-coordinate of the position to check for collisions.
 * @param y The parameter "y" represents the y-coordinate of the position being checked for collisions.
 * @return The method is returning a boolean value.
 */
    protected boolean checkCollisions(int x, int y) {
        for (Entity entity : entities) {
            if (this != entity && (collidesWithOtherEntity(entity, x, y))) {
                return false;
            }
        }
        return true;
    }

/**
 * The function moves an object in a specified direction if there are no collisions.
 * 
 * @param directionX The amount to move in the X direction.
 * @param direction The direction parameter represents the change in the y-coordinate of the position.
 */
    protected void moveDirection(double directionX, double direction) {
        if (!(directionX == 0 && direction == 0)) {
            double x = position.getX() + directionX;
            double y = position.getY() + direction;
            if (checkCollisions((int) x, (int) y)) {
                position.setX(x);
                position.setY(y);
            }
        }
    }

    /**
     * The function "paint" is an abstract method that does not have an implementation and must be
     * overridden by any class that extends the abstract class containing this method.
     */
    public abstract void paint();

    /**
     * The function returns the value of the speed variable.
     * 
     * @return The method is returning the value of the variable "speed".
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * The function sets the speed of an object.
     * 
     * @param speed The "speed" parameter is an integer that represents the speed value to be set.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
