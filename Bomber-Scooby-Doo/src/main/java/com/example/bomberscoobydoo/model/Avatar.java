package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;

public abstract class Avatar  extends Entity{


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

    public Avatar(Canvas canva, Vector position, Destructible destructible) {
        super(canva, position, destructible);
    }

    public void onMove(){
        collisionWithCanva();
        int directionX = 0;
        int directionY = 0;
        if (goUp && !upCollision){
            directionY = -speed;
        }
        if (goDown && !downCollision){
            directionY = speed;
        }
        if (goLeft && !leftCollision){
            directionX = -speed;
        }
        if (goRight && !rightCollision){
            directionX = speed;
        }
        moveDirection(directionX,directionY);
    }

    protected boolean collisionWithCanva(){
        if(position.getX() < 10 )
            leftCollision=true;
        else
            leftCollision=false;
        if(position.getX() > canvas.getWidth()-65)
            rightCollision =true;
        else
            rightCollision =false;
        if(position.getY() < 10)
            upCollision=true;
        else
            upCollision=false;
        if(position.getY() > canvas.getHeight()-70)
            downCollision=true;
        else
            downCollision=false;
        return false;
    }

    protected boolean collidesWithOtherEntity(Entity other, int x, int y) {
        collision = false;
        if(other instanceof Bomb && this instanceof Player){
            ((Bomb) other).checkIfPlayerOutSideBomb(x, y);
        }
        if (this != other && !(other instanceof Explosion) &&
                (!(other instanceof Bomb) || (other instanceof Bomb && ((Bomb)other).isPlayerOutSideBomb())
                ) ) {
            int epsilon = 45;
            if(this instanceof Enemy){
                epsilon = 38;
            }
            collision = !(x + epsilon < other.position.getX() ||
                    x > other.position.getX() + epsilon ||
                    y + epsilon < other.position.getY() -10 ||
                    y > other.position.getY() + epsilon);

            if(this instanceof Player && other instanceof Enemy && collision){
                ((Player)this).setLife(((Player)this).getLife()-1);
                Vector vector = new Vector(0,0);
                this.setPosition(vector);
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

    public abstract void paint();
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
