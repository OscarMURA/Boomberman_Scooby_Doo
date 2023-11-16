package com.example.bomberscoobydoo.screens;

import com.example.bomberscoobydoo.control.BomberGameControler;
import com.example.bomberscoobydoo.effects.AudioManager;
import com.example.bomberscoobydoo.model.MoveType;
import com.example.bomberscoobydoo.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;


import java.util.ArrayList;

public abstract class BaseScreen  {
    protected long startTime;
    protected Image imageLevel;
    protected boolean level;
    protected  static Entity player;
    protected ArrayList<Entity> entities;
    protected Image background;
    protected Canvas canvas;
    protected GraphicsContext graphicsContext;
    protected AudioManager audio = AudioManager.getInstance();
    public BaseScreen(Canvas canvas) {
        startTime = System.currentTimeMillis();
        level= true;
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
        player = BomberGameControler.getInstance().getPlayer();
    }

    public abstract void paint();

    public void putBomb(){
        boolean somethingAlreadyThere;
        Vector ubicationOfBomb = player.getPosition().clone();
        ubicationOfBomb.setX(ubicationOfBomb.getX() + 30);
        ubicationOfBomb.setY(ubicationOfBomb.getY() + 30);
        ubicationOfBomb = getBlockByPosition(ubicationOfBomb);
        somethingAlreadyThere = checkIfEntityIsThere(ubicationOfBomb);
        if(!somethingAlreadyThere){
            ((Player)(player)).setAmountBombs(((Player)(player)).getAmountBombs()-1);
            entities.add(new Bomb(canvas, ubicationOfBomb, ((Player)(player)).getIntensityOfExplosions()));
        }
    }

    protected Vector getBlockByPosition(Vector vector){
        double x = vector.getX();
        double y = vector.getY();
        return new Vector((Math.floor(x/60))*60, (Math.floor(y/60)*60));
    }

    protected void checkExplosions(){
        ArrayList<Entity> toRemove = new ArrayList<>();
        for(Entity e : entities){
            if(e instanceof Bomb && System.currentTimeMillis() - ((Bomb)e).getStartTime() >= 2000){
                toRemove.add(e);

            }else if(e instanceof Explosion && System.currentTimeMillis() - ((Explosion)e).getStartTime() >= 600){
                toRemove.add(e);
            }
        }
        for(Entity e : toRemove){
            entities.remove(e);
            e.removeEntity(e);
            if(e instanceof Bomb){
                Vector position = e.getPosition().clone();
                int intensity = ((Player)(player)).getIntensityOfExplosions();

                explodeBomb(intensity, position, MoveType.STOP);
            }
            e = null;
        }

    }

    private void explodeBomb(int intensity, Vector position, MoveType direction){
        if(intensity >= 0){
            if(!checkIfEntityIsThere(position)) {
                entities.add(new Explosion(canvas, position.clone(), intensity));
                if(intensity > 0){
                    switch (direction) {
                        case STOP -> {
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.LEFT), MoveType.LEFT);
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.RIGHT), MoveType.RIGHT);
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.UP), MoveType.UP);
                            explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.DOWN), MoveType.DOWN);
                        }
                        case LEFT -> explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.LEFT), MoveType.LEFT);
                        case RIGHT -> explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.RIGHT), MoveType.RIGHT);
                        case UP -> explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.UP), MoveType.UP);
                        case DOWN -> explodeBomb(intensity - 1, getFollowingBlock(position, MoveType.DOWN), MoveType.DOWN);
                    }

                }
            }else{
                boolean foundBox = false;
                Entity e = null;
                for(int i = 0; i < entities.size() && !foundBox; i++){
                    e = entities.get(i);
                    if(position.equals(e.getPosition())){
                        foundBox = true;
                    }
                }
                if(e != null && e.getDestructible() == Destructible.DESTRUCTIBLE){
                    entities.add(new Explosion(canvas, e.getPosition(), intensity));
                    eliminateEntity(e);
                }
            }
        }

    }

    public void eliminateEntity(Entity e){
        entities.remove(e);
        e.removeEntity(e);
        e = null;
    }

    private boolean checkIfEntityIsThere(Vector position){
        boolean someThingAlreadyThere = false;
        for(int i = 0; i < entities.size() && !someThingAlreadyThere; i++){

            Entity e = entities.get(i);

            if(e.getPosition().equals(position)){
                someThingAlreadyThere = true;
            }

        }
        return someThingAlreadyThere;
    }

    protected void checkPlayerOverExplosion(){

        boolean someThingAlreadyThere = false;
        Vector edge1 = player.getPosition().clone();
        Vector edge2 = player.getPosition().clone();
        edge2.setX(edge2.getX() + 54); edge2.setY(edge2.getY() + 54);
        Vector edge3 = player.getPosition().clone();
        edge3.setX(edge3.getX() + 54);
        Vector edge4 = player.getPosition().clone();
        edge4.setY(edge4.getY() + 54);

        for(int i = 0; i <entities.size() && ! someThingAlreadyThere; i++) {
            Entity e = entities.get(i);
            if (e instanceof Explosion &&
                    System.currentTimeMillis() - ((Player)(player)).getInvensibilityStartTime() > 600 &&
                    (e.getPosition().equals(getBlockByPosition(edge1)) ||
                            e.getPosition().equals(getBlockByPosition(edge2)) ||
                            e.getPosition().equals(getBlockByPosition(edge3)) ||
                            e.getPosition().equals(getBlockByPosition(edge4)) ) ){

                someThingAlreadyThere = true;
                ((Player) (player)).lowerByOneLife();
                ((Player) (player)).setInvensibilityStartTime();
                entities.remove(e);
                e.removeEntity(e);
            }
        }
    }

    private Vector getFollowingBlock(Vector position, MoveType direction) {
        Vector newPosition = position.clone();
        switch (direction){
            case UP -> newPosition.setY(newPosition.getY() - 60);
            case DOWN -> newPosition.setY(newPosition.getY() + 60);
            case LEFT -> newPosition.setX(newPosition.getX() - 60);
            case RIGHT -> newPosition.setX(newPosition.getX() + 60);
        }
        return newPosition;
    }


    public void onKeyPressed(KeyEvent event){
        ((Player ) player).onKeyPressed(event);
    }

    public void onKeyReleased(KeyEvent event){
        ((Player ) player).onKeyReleased(event);
    }

}
