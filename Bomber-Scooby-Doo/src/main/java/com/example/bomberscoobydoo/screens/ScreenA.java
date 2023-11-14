package com.example.bomberscoobydoo.screens;

import com.example.bomberscoobydoo.PlayGame;
import com.example.bomberscoobydoo.control.BomberGameControler;
import com.example.bomberscoobydoo.model.Entity;
import com.example.bomberscoobydoo.model.Bricks;
import com.example.bomberscoobydoo.model.Vector;
import com.example.bomberscoobydoo.model.Wall;
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

        for (Entity entity : entities) {
            entity.paint();
        }
    }
}