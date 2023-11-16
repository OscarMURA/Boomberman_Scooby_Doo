package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Avatar{

    private ArrayList<Image> images;
    private Entity player;
    private EnemyType type;
    private final double MAXSTEPS;

    public Enemy(Canvas canva, Vector position, Entity player, String type){

        super(canva,position,Destructible.DESTRUCTIBLE);
        type = type.toUpperCase();
        images = new ArrayList<>();
        this.player = player;
        MAXSTEPS = 2;
        if(type=="BLINDY")
            this.type = EnemyType.BLINDY;
        else if(type=="PINKY")
            this.type= EnemyType.PINKY;
        else if(type=="SLENDY")
            this.type= EnemyType.SLENDY;

        for (int i = 1; i <= 8; i++) {
            images.add(new Image(getClass().getResourceAsStream("/images/enemies/"+this.type+"/walk/walk-0"+i+".png"),40,40,false,false));
        }
        speed = 5;
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
        onMove();
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(images.get(frame%8),position.getX(),position.getY());
        frame++;
    }

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


    private void updateEnemy(){
        Vector vector = new Vector(player.getPosition().getX() - getPosition().getX(),player.getPosition().getY()-getPosition().getX());
        MoveType direction=null;

        if(vector.normalize()<333.0){
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


    public MoveType calculateLow() {
        Random random = new Random();
        MoveType[] directions = MoveType.values();
        MoveType result=null;
        if(steps++ >= 10) {
            steps = 0;
            result= MoveType.STOP;
        }else{
            result= directions[random.nextInt(4)];
        }
        return result;
    }
}
