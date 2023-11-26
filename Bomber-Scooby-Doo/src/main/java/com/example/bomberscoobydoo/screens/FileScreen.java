package com.example.bomberscoobydoo.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.example.bomberscoobydoo.PlayGame;
import com.example.bomberscoobydoo.control.BomberGameControler;
import com.example.bomberscoobydoo.model.Bricks;
import com.example.bomberscoobydoo.model.Player;
import com.example.bomberscoobydoo.model.Wall;
import com.example.bomberscoobydoo.model.Vector;
import javafx.scene.canvas.Canvas;
import com.example.bomberscoobydoo.model.Entity;

public class FileScreen {

    private int height;
    private int width;
    private char[][] levelData;
    private ArrayList<Entity> entities;

    public FileScreen() {
        entities = new ArrayList<>();
    }

    public void loadLevel(int level) {
        System.out.println("Loading level " + level + "...");
        try {
            InputStream input = PlayGame.class.getResourceAsStream("/levels/level" + level + ".txt");
            assert input != null;
            System.out.println("Loading level " + level + "...");
            InputStreamReader fileReader = new InputStreamReader(input);
            BufferedReader in = new BufferedReader(fileReader);
            String metaDataLine = in.readLine();
            System.out.println(metaDataLine);
            StringTokenizer metaDataTokens = new StringTokenizer(metaDataLine);
            level = Integer.parseInt(metaDataTokens.nextToken());
            height = Integer.parseInt(metaDataTokens.nextToken());
            width = Integer.parseInt(metaDataTokens.nextToken());
            levelData = new char[height][width];
            for (int i = 0; i < height; i++) {
                String line = in.readLine();
                if (line != null) {
                    line = line.substring(0, Math.min(line.length(), width));
                    levelData[i] = line.toCharArray();
                } else {
                    throw new RuntimeException("La línea leída es nula o más corta que el ancho esperado.");
                }
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createEntities(Canvas canvas) {
        for (int i = 0; i < height; i++) {
            char[] line = levelData[i];
            for (int j = 0; j < line.length; j++) {
                char c = line[j];
                addLevelEntity(canvas, c, j * 60, i * 60);
            }

        }
    }

    private void addLevelEntity(Canvas canvas, char c, int x, int y) {
        Vector position = new Vector(x, y);
        switch (c) {
            case '#':
                entities.add(new Wall(canvas, position));
                break;
            case '*':
                entities.add(new Bricks(canvas, position));
                break;
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}