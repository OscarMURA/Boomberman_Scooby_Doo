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
        if(ControlUser.getInstance().bomb && ((Player)(player)).getAmountBombs()>0){
            ((Player)(player)).setAmountBombs(((Player)(player)).getAmountBombs()-1);
            entities.add(new Bomb(canvas, player.getPosition().clone()));
            ControlUser.getInstance().bomb = false;
        }
        for (Entity entity: entities) {
            entity.paint();
        }
    }
}
