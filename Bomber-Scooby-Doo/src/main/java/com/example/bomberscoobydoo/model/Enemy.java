package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Enemy class is a subclass of the Avatar class, it represents the foes in the game.
 */
public class Enemy extends Avatar{

    private ArrayList<Image> images;
    private Entity player;
    private EnemyType type;
    private final double MAXSTEPS;

// The `Enemy` constructor is initializing a new instance of the `Enemy` class. It takes in several
// parameters: `canva` (a `Canvas` object), `position` (a `Vector` object), `player` (an `Entity`
// object), and `type` (a `String`).
    public Enemy(Canvas canva, Vector position, Entity player, String type){
        super(canva,position,Destructible.DESTRUCTIBLE);
        type = type.toUpperCase();
        images = new ArrayList<>();
        this.player = player;
        MAXSTEPS = 4;
        if(type=="BLINDY"){
            this.type = EnemyType.BLINDY;
            speed=5;
        }
        else if(type=="PINKY"){
            speed=8;
            this.type= EnemyType.PINKY;
        }
        else if(type=="SLENDY"){
            speed=10;
            this.type= EnemyType.SLENDY;
        }
        for (int i = 1; i <= 8; i++) {
            images.add(new Image(getClass().getResourceAsStream("/images/enemies/"+this.type+"/walk/walk-0"+i+".png"),40,40,false,false));
        }
    }

    /**
     * The function checks for collisions between the current entity and another entity, and performs
     * different actions based on the type of the other entity.
     * 
     * @param other The "other" parameter is an instance of the Entity class, which represents another
     * entity in the game world. It could be a player, an enemy, or a power-up, depending on the
     * context in which this method is called.
     * @param x The x-coordinate of the position to check for collision with the other entity.
     * @param y The parameter "y" represents the y-coordinate of the position where the collision is
     * being checked.
     * @return The method is returning the value of the variable "collision".
     */
    public boolean collidesWithOtherEntity(Entity other, int x, int y){
          collision = super.collidesWithOtherEntity(other,x,y);
        if(collision && other instanceof Player){
            Player player = (Player) other;
            player.lowerByOneLife();
            Vector vector = new Vector(0,0);
            player.setPosition(vector);
        }
        if(collision && other instanceof Enemy){
            collision= false;
        }
        if(collision && other instanceof Power){
            other.setPosition(new Vector(1300,800));
        }
        return collision;
    }


    /**
     * The paint function updates the enemy, performs a move, and then draws an image on a canvas.
     */
    @Override
    public void paint() {
        updateEnemy();
        onMove();
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(images.get(frame%8),position.getX(),position.getY());
        frame++;
    }

    /**
     * The function calculates a random direction to move in, either horizontally or vertically, and
     * returns the direction.
     * 
     * @return The method is returning a MoveType, which represents the direction of movement.
     */
    public MoveType calculate() {
        if(steps++ > MAXSTEPS) {
            steps = 0;
            return MoveType.STOP;
        }
        Random random= new Random();
        int rand = random.nextInt(2);
        MoveType direction;
        if (rand == 1) {
            direction = calculateRow();
            if (direction == MoveType.STOP) {
                direction = calculateCol();
            }
        } else {
            direction = calculateCol();
            if (direction == MoveType.STOP) {
                direction = calculateRow();
            }
        }
        return direction;
    }


    /**
     * The function `updateEnemy()` calculates the direction for an enemy to move towards the player,
     * based on their positions, and sets the corresponding movement flags.
     */
    private void updateEnemy(){
        Vector vector = new Vector(player.getPosition().getX() - getPosition().getX(),player.getPosition().getY()- getPosition().getX());
        MoveType direction=null;
        if( vector.normalize()<600.0){

            direction = calculate();
        }else{
            direction = calculateLow();
        }

        if (direction == MoveType.STOP || collision) {
            isMoving = false;
        }
        if (!isMoving) {
            goUp = goDown = goLeft = goRight = false;
            isMoving = true;
            switch (direction) {
                case UP -> goUp = true;
                case DOWN -> goDown = true;
                case LEFT -> goLeft = true;
                case RIGHT -> goRight = true;
                default -> isMoving = false;
            }
        }
    }

    /**
     * The function calculates the appropriate move type for an enemy based on the relative positions
     * of the player and the enemy on the x-axis.
     * 
     * @return The method is returning a MoveType, which is either MoveType.STOP, MoveType.LEFT, or
     * MoveType.RIGHT.
     */
    private MoveType calculateCol() {
        int playerX = player.getPosition().getTileX();
        int enemyX = getPosition().getTileX();
        MoveType result= MoveType.STOP;
        if(playerX < enemyX) {
            result= MoveType.LEFT;
        }
        if(playerX > enemyX) {
            result= MoveType.RIGHT;
        }
        return result;
    }

    private MoveType calculateRow() {
    // The code block you provided is a method called `calculateRow()` in the `Enemy` class. This method
    // calculates the appropriate move type for an enemy based on the relative positions of the player and
    // the enemy on the y-axis.
        MoveType result=MoveType.STOP;
        int playerY = player.getPosition().getTileY();
        int enemyY = getPosition().getTileY();
        if(playerY < enemyY) {
            result= MoveType.UP;
        }
        if(playerY > enemyY) {
            result= MoveType.DOWN;
        }
        return result;
    }


    /**
     * The function calculates a random MoveType direction, but returns MoveType.STOP every 4 steps.
     * 
     * @return The method is returning a MoveType, which is either a random direction (UP, DOWN, LEFT, or
     * RIGHT) or STOP.
     */
    public MoveType calculateLow() {
        Random random = new Random();
        MoveType[] directions = MoveType.values();
        MoveType result=null;
        if(steps++ >= 4) {
            steps = 0;
            result= MoveType.STOP;
        }else{
            result= directions[random.nextInt(4)];
        }
        return result;
    }
}
