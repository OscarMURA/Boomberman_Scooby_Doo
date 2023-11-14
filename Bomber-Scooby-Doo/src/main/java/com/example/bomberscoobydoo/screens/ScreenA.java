package com.example.bomberscoobydoo.screens;

import com.example.bomberscoobydoo.PlayGame;
import com.example.bomberscoobydoo.control.BomberGameControler;
import com.example.bomberscoobydoo.effects.ControlUser;
import com.example.bomberscoobydoo.effects.MoveType;
import com.example.bomberscoobydoo.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class ScreenA extends BaseScreen {

    private Entity player;
    private ArrayList<Entity> entities;
    private Image background;

    // La matriz de obstáculos
    private Integer obstaclesInMap[][] = new Integer[][] {
            { null, 1, null, null, 1, null, null, 1, 2, 2, 1, 2, null, 1, 2, 2, 1,
                    2, 1, null },
            { null, null, null, null, 2, 2, null, 2, 2, 2, 2, 2, null, null, null, null, null, 2, 2,
                    2 },
            { null, 1, null, 2, 1, null, 2, 1, null, null, 1, null, 2, 1, null, null, 1, 2, 1, null },
            { 2, 2, 2, null, 2, 2, null, null, null, null, null, null, null, 2, null, 1, null, null, 2,
                    null },
            { 2, 1, 2, null, 1, null, 2, 1, 2, 2, 1, 2, null, 1, null, null, 1, 2, 1, null },
            { 2, 2, 2, 2, 2, null, 1, 2, 1, null, null, 2, 2, 2, 2, 1, 2, 2, null, null },
            { null, 1, 2, 2, 1, null, 2, 1, 2, null, 1, 2, null, 1, null, 2, 1, 2, 1, null },
            { null, 2, null, null, 2, null, null, 2, null, null, 1, null, null, 2, null, null, 2, null, 2,
                    null },
            { null, 1, null, null, 1, null, null, 1, 2, 2, 1, 2, null, 1, null, null, 1, 2, 1, null },
            { null, 2, 2, 2, null, null, 2, null, 2, 2, null, 2, 2, 1, null, 2, 1, 2, 2,
                    null } };


    public ScreenA(Canvas canvas) {
        super(canvas);
        background = new Image("floorClare.png");
        player = BomberGameControler.getInstance().getPlayer();
        entities = new ArrayList<>();

        for (int i = 0; i < obstaclesInMap.length; i++) {
            for (int j = 0; j < obstaclesInMap[i].length; j++) {
                if (obstaclesInMap[i][j] != null) {
                    Vector position = new Vector(j * 60, i * 60);
                    if (obstaclesInMap[i][j] == 1) {
                        entities.add(new Wall(canvas, position));
                    } else if (obstaclesInMap[i][j] == 2) {
                        entities.add(new Bricks(canvas, position));
                    }
                }
            }
        }

        entities.add(player);

    }

    @Override
    public void paint() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
        //Poner y verificar bomba
        if(ControlUser.getInstance().bomb && ((Player)(player)).getAmountBombs()>-100){
            //for testing purposes you can put up to 10 bombs, then change the -10 to 0
            ControlUser.getInstance().bomb = false;
            putBomb();
            //explodeBomb(5, new Vector(480, 480), MoveType.STOP);
        }

        checkExplosions();

        checkPlayerOverExplosion();

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

    private Entity checkIfDestructableEntityIsThere(Vector position){
        Entity someThingAlreadyThere = null;
        for(int i = 0; i < entities.size() && someThingAlreadyThere != null; i++){

            Entity e = entities.get(i);

            if(e.getPosition().equals(position) &&
                    e.getDestructible() == Destructible.DESTRUCTIBLE){

                someThingAlreadyThere = e;
            }

        }
        System.out.println("someThingAlreadyThere:" + someThingAlreadyThere);
        return someThingAlreadyThere;
    }

    private void checkPlayerOverExplosion(){
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
