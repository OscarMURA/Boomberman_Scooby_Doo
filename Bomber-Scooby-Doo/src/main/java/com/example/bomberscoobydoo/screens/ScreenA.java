package com.example.bomberscoobydoo.screens;

import com.example.bomberscoobydoo.control.BomberGameControler;

import com.example.bomberscoobydoo.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class ScreenA extends BaseScreen {

    private Enemy enemy;
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
                    3 } };


    public ScreenA(Canvas canvas) {
        super(canvas);
        audio.setMusicPath("/Scooby-Doo-Underscore.wav");
        audio.playEffectInBackground("/scooby.wav");
        audio.playMusic(1000);
        background = new Image("floorClare.png");
        player = BomberGameControler.getInstance().getPlayer();
        entities = new ArrayList<>();
        imageLevel=new Image(getClass().getResourceAsStream("/images/Banner/level1.png"));
        for (int i = 0; i < obstaclesInMap.length; i++) {
            for (int j = 0; j < obstaclesInMap[i].length; j++) {
                if (obstaclesInMap[i][j] != null) {
                    Vector position = new Vector(j * 60, i * 60);
                    if (obstaclesInMap[i][j] == 1) {
                        entities.add(new Wall(canvas, position));
                    } else if (obstaclesInMap[i][j] == 2) {
                        entities.add(new Bricks(canvas, position));
                    }else if (obstaclesInMap[i][j] == 3) {
                        entities.add(new Door(canvas, position));
                    }
                }
            }
        }
        Vector pos=new Vector(60,60);
        Vector pos1=new Vector (70,70);
        entities.add(new Bricks(canvas,pos));
        entities.add(new Power(canvas,pos1,"LIFE_PLUS"));
        entities.add(new Power(canvas,pos1,"FIRE_FRIEND"));
        entities.add(new Power(canvas,new Vector(10, 190),"FIRE_PLUS"));
        entities.add(new Power(canvas,new Vector(550, 310),"SPEED"));
        entities.add(new Power(canvas,new Vector(250, 550),"BOMB_PLUS"));
        entities.add(player);
        entities.add(new Enemy(canvas, new Vector(330, 0), player,"SLENDY"));
        player.setEntities(entities);

    }

    @Override
    public void paint() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
        //Poner y verificar bomba
        if(((Player) player).putBomb() && ((Player)(player)).getAmountBombs()> -100){
            ((Player) player).setBomb(false);
            putBomb();
            //explodeBomb(5, new Vector(480, 480), MoveType.STOP);
        }
        checkExplosions();
        checkEnemyOverExplosion();
        if(!((Player)player).isPowerFireFriends()) checkPlayerOverExplosion();
        for(Entity entity: entities){
            if(entity instanceof Power){
                entity.paint();
            }
        }
        for (Entity entity: entities) {
            if(!(entity instanceof Power)){
                entity.paint();
            }
        }
        //Show level for 5 seconds
        if(level) {
            graphics.drawImage(imageLevel, 0, 0, canvas.getWidth(), canvas.getHeight());
            if(System.currentTimeMillis()-BomberGameControler.getInstance().getTime()>5000){
                level=false;
            }
        }
    }



}
