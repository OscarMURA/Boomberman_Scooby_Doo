package com.example.bomberscoobydoo.screens;

import com.example.bomberscoobydoo.control.BomberGameControler;
import com.example.bomberscoobydoo.effects.ControlUser;
import com.example.bomberscoobydoo.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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

        }
        for (Entity entity: entities) {
            entity.paint();
        }
    }

    public void putBomb(){
        boolean somethingAlreadyThere = false;
        Vector ubicationOfBomb = player.getPosition().clone();
        ubicationOfBomb.setX(ubicationOfBomb.getX() + 30);
        ubicationOfBomb.setY(ubicationOfBomb.getY() + 30);
        ubicationOfBomb = getBlockByPosition(ubicationOfBomb);

        //System.out.println("x: " + ubicationOfBomb.getX() + " y: " + ubicationOfBomb.getY());
        for(Entity e : entities){

            if(e.getPosition().equals(ubicationOfBomb)){
                somethingAlreadyThere = true;
            }

        }
        System.out.println("hay cosa" + somethingAlreadyThere);

        if(!somethingAlreadyThere){
            ((Player)(player)).setAmountBombs(((Player)(player)).getAmountBombs()-1);
            entities.add(new Bomb(canvas, ubicationOfBomb, ((Player)(player)).getIntensityOfExplosions()));
            System.out.println("bomba puesta " + ((Player)(player)).getAmountBombs());
        }
    }

    protected Vector getBlockByPosition(Vector vector){
        double x = vector.getX();
        double y = vector.getY();
        return new Vector((Math.floor(x/60))*60, (Math.floor(y/60)*60));
    }

}
