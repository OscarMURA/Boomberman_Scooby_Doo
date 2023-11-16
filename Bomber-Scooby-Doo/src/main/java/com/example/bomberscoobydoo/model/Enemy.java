package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity{

    private ArrayList<Image> images;
    private Entity player;
    private boolean goUp;
    private boolean goDown;
    private boolean goLeft;
    private boolean goRight;
    private double steps;
    private final double MAXSTEPS=3;
    private boolean isMoving;
    private int frame;


    public Enemy(Canvas canva, Vector position, Entity player){
        super(canva,position,Destructible.DESTRUCTIBLE);
        images = new ArrayList<>();
        this.player = player;
        for (int i = 1; i <= 8; i++) {
            images.add(new Image(getClass().getResourceAsStream("/images/enemies/Blindy/walk/walk-0"+i+".png"),40,40,false,false));
        }
    }

    public boolean collidesWithOtherEntity(Entity other, int x, int y){
          collision = super.collidesWithOtherEntity(other,x,y);
        if(collision && other instanceof Player){
            Player player = (Player) other;
            player.setLife(player.getLife()-1);
            Vector vector = new Vector(0,0);
            player.setPosition(vector);
        }

        if(collision && other instanceof Enemy){
            collision= false;
        }
        return collision;
    }


    @Override
    public void paint() {
        updateEnemy();
        walk();
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(images.get(frame%8),position.getX(),position.getY());
        frame++;
    }



    private void updateEnemy(){
        int direction = calculate();
        if (direction == -1 || collision) {
            isMoving = false;
        }
        if (!isMoving) {
            goUp = goDown = goLeft = goRight = false;
            isMoving = true;
            switch (direction) {
                case 0 -> goUp = true;
                case 1 -> goDown = true;
                case 2 -> goLeft = true;
                case 3 -> goRight = true;
                default -> isMoving = false;
            }
        }
    }

    private void walk(){
        collisionWithCanva();
        int dx = 0;
        int dy = 0;
        if (goUp&& !upCollision) {
            dy -= 5;
        }
        if (goDown && !downCollision) {
            dy += 5;
        }
        if (goLeft && !leftCollision) {
            dx -= 5;
        }
        if (goRight && !rightCollision)  {
            dx += 5;
        }
        moveDirection(dx, dy);

    }

    public int calculate() {
        if(steps++ > MAXSTEPS) {
            steps = 0;
            return -1;
        }
        Random random= new Random();
        int rand = random.nextInt(2);
        int direction;
        if (rand == 1) {
            direction = calculateRow();
            if (direction == -1) {
                direction = calculateCol();
            }
        } else {
            direction = calculateCol();
            if (direction == -1) {
                direction = calculateRow();
            }
        }
        return direction;
    }

    private int calculateCol() {
        int playerX = player.getPosition().getTileX();
        int playerY = player.getPosition().getTileY();
        int enemyX = getPosition().getTileX();
        int enemyY = getPosition().getTileY();

        int result=-1;
        if(playerX < enemyX) {
            result= 2;
        }
        if(playerX > enemyX) {
            result= 3;
        }
        return result;
    }
    private int calculateRow() {
        int result=-1;
        int playerX = player.getPosition().getTileX();
        int playerY = player.getPosition().getTileY();
        int enemyX = getPosition().getTileX();
        int enemyY = getPosition().getTileY();
        if(playerX < enemyX) {
            result= 0;
        }
        if(playerY > enemyY) {
            result= 1;
        }
        return result;
    }
}
