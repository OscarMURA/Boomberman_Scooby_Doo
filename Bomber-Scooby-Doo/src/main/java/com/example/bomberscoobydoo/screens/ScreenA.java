package com.example.bomberscoobydoo.screens;

import com.example.bomberscoobydoo.control.BomberGameControler;
import com.example.bomberscoobydoo.model.Entity;
import com.example.bomberscoobydoo.model.Bricks;
import com.example.bomberscoobydoo.model.Vector;
import com.example.bomberscoobydoo.model.Wall;
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
        for (Entity entity: entities) {
            entity.paint();
        }
        player.paint();

    }
}
