package com.example.bomberscoobydoo.screens;

import com.example.bomberscoobydoo.control.BomberGameControler;
import com.example.bomberscoobydoo.effects.ControlUser;
import com.example.bomberscoobydoo.effects.MoveType;
import com.example.bomberscoobydoo.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class ScreenA extends BaseScreen{

    private Entity player;
    private ArrayList<Entity> entities;

    public ScreenA(Canvas canvas) {
        super(canvas);
        player=BomberGameControler.getInstance().getPlayer();
        entities = new ArrayList<>();
        Vector position = new Vector(120,120);
        entities.add(new Wall(canvas,position));
        position = new Vector(180,180);
        entities.add(new Wall(canvas,position));
        position = new Vector(240,240);
        entities.add(new Bricks(canvas,position));
        position = new Vector(300,300);
        entities.add(new Bricks(canvas,position));
        position = new Vector(360,360);
        entities.add(new Bricks(canvas,position));

    }

    @Override
    public void paint() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        player.paint();

        //Poner y verificar bomba
        if(ControlUser.getInstance().bomb && ((Player)(player)).getAmountBombs()>-10){
            //for testing purposes you can put up to 10 bombs, then change the -10 to 0
            ControlUser.getInstance().bomb = false;
            putBomb();
            //explodeBomb(5, new Vector(480, 480), MoveType.STOP);
        }

        checkExplosions();

        for (Entity entity: entities) {
            entity.paint();
        }
    }

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

    private void checkExplosions(){
        ArrayList<Entity> toRemove = new ArrayList<>();
        for(Entity e : entities){
            if(e instanceof Bomb && System.currentTimeMillis() - ((Bomb)e).getStartTime() >= 2000){
               toRemove.add(e);

            }else if(e instanceof Explosion && System.currentTimeMillis() - ((Explosion)e).getStartTime() >= 900){
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

                System.out.println("Explotando");

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

            }
        }

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

    private Vector getFollowingBlock(Vector position, MoveType direction){

        Vector newPosition = position.clone();

        switch (direction){
            case UP -> newPosition.setY(newPosition.getY() - 60);
            case DOWN -> newPosition.setY(newPosition.getY() + 60);
            case LEFT -> newPosition.setX(newPosition.getX() - 60);
            case RIGHT -> newPosition.setX(newPosition.getX() + 60);
        }

        return newPosition;
    }

}
