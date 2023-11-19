package com.example.bomberscoobydoo.screens;


import com.example.bomberscoobydoo.control.BomberGameControler;

import com.example.bomberscoobydoo.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class ScreenC extends BaseScreen {

    private Entity player;
    private ArrayList<Entity> entities;
    private Image background;

    private Integer obstaclesInMap[][] = new Integer[][] {
            { null, 1, null, null, 1, null, null, 1, null, null, 1, null, null, 1, null, null, 1,
                    null, 1, null },
            { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 2,
                    2,
                    null },
            { null, 1, null, null, 1, null, null, 1, null, null, 1, null, null, 1, null, null, 1, null, 1, null },
            { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    null, null,
                    null },
            { null, 1, null, null, 1, null, null, 1, null, null, 1, null, null, 1, null, null, 1, null, 1, null },
            { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    null, null, null },
            { null, 1, null, null, 1, null, null, 1, null, null, 1, null, null, 1, null, null, 1, null, 1, null },
            { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    null, null,
                    null },
            { null, 1, null, null, 1, null, null, 1, null, null, 1, null, null, 1, null, null, 1, null, 1, null },
            { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    null, null,
                    null } };

    public ScreenC(Canvas canvas) {
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
        player.setEntities(entities);
    }

    @Override
    public void paint() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
        //Poner y verificar bomba
        if(((Player) player).putBomb() && ((Player)(player)).getAmountBombs()>-100){
            //for testing purposes you can put up to 10 bombs, then change the -10 to 0
            ((Player) player).setBomb(false);
            putBomb();
            //explodeBomb(5, new Vector(480, 480), MoveType.STOP);
        }
        checkExplosions();
        if(!((Player)player).isPowerFireFriends()) checkPlayerOverExplosion();
        for (Entity entity : entities) {
            entity.paint();
        }
        player.paint();

        if(level) {
            graphics.drawImage(imageLevel, 0, 0, canvas.getWidth(), canvas.getHeight());
            if(System.currentTimeMillis()-BomberGameControler.getInstance().getTime()>12000){
                level=false;
            }
        }
    }
}