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

    public abstract void paint();
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
