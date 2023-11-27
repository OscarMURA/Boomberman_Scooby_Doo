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
import com.example.bomberscoobydoo.model.Door;
import com.example.bomberscoobydoo.model.Enemy;
import com.example.bomberscoobydoo.model.Player;
import com.example.bomberscoobydoo.model.Power;
import com.example.bomberscoobydoo.model.Wall;
import com.example.bomberscoobydoo.model.Vector;
import javafx.scene.canvas.Canvas;
import com.example.bomberscoobydoo.model.Entity;

/**
 * The FileScreen class is used for file screening operations.
 */
public class FileScreen {

    private int height;
    private int width;
    private char[][] levelData;
    private Player player;
    private ArrayList<Entity> entities;
    private int level;

    // The `public FileScreen()` constructor is initializing a new instance of the
    // `FileScreen` class.
    public FileScreen() {
        this.player = BomberGameControler.getInstance().getPlayer();
        entities = new ArrayList<>();
    }

    /**
     * The function loads a level from a text file and stores the level data in a 2D
     * character array.
     * 
     * @param level The level parameter is an integer that represents the level
     *              number to be loaded.
     */
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
            this.level = Integer.parseInt(metaDataTokens.nextToken());
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

    /**
     * The function creates entities on a canvas based on the level data provided.
     * 
     * @param canvas The `Canvas` parameter represents the canvas on which the
     *               entities will be
     *               created. It is likely an object that provides a drawing surface
     *               for rendering graphics.
     */
    public void createEntities(Canvas canvas) {
        for (int i = 0; i < height; i++) {
            char[] line = levelData[i];
            for (int j = 0; j < line.length; j++) {
                char c = line[j];
                addLevelEntity(canvas, c, j * 60, i * 60);
            }

        }
    }

    /**
     * The function adds different types of entities to a canvas based on the given
     * character.
     * 
     * @param canvas The `canvas` parameter is an object of type `Canvas`. It
     *               represents the canvas on
     *               which the entities will be drawn.
     * @param c      The parameter 'c' represents the character that determines the
     *               type of entity to be
     *               added to the canvas.
     * @param x      The x parameter represents the x-coordinate of the entity's
     *               position on the canvas. It
     *               determines the horizontal position of the entity.
     * @param y      The parameter `y` represents the y-coordinate of the position
     *               where the entity will be
     *               added on the canvas.
     */
    private void addLevelEntity(Canvas canvas, char c, int x, int y) {
        Vector position = new Vector(x, y);
        switch (c) {
            case '*':
                entities.add(new Wall(canvas, position));
                break;

            case '#':
                entities.add(new Bricks(canvas, position));
                break;
            case '$':
                entities.add(new Door(canvas, position));
                break;
            case 'b':
                entities.add(new Enemy(canvas, position, player, "BLINDY"));
                break;
            case 's':
                entities.add(new Enemy(canvas, position, player, "SLENDY"));
                break;
            case 'p':
                entities.add(new Enemy(canvas, position, player, "PINKY"));
                break;
            case 'o':
                position.setX(position.getX() + 10);
                position.setY(position.getY() + 10);
                entities.add(new Power(canvas, position, "FIRE_PLUS"));
                break;
            case 'f':
                position.setX(position.getX() + 10);
                position.setY(position.getY() + 10);
                entities.add(new Power(canvas, position, "FIRE_FRIEND"));
                break;
            case '%':
                position.setX(position.getX() + 10);
                position.setY(position.getY() + 10);
                entities.add(new Power(canvas, position, "BOMB_PLUS"));
                break;
            case 'r':
                position.setX(position.getX() + 10);
                position.setY(position.getY() + 10);
                entities.add(new Power(canvas, position, "SPEED"));
                break;
            case 'l':
                position.setX(position.getX() + 10);
                position.setY(position.getY() + 10);
                entities.add(new Power(canvas, position, "LIFE_PLUS"));
                break;
        }
    }

    // The `getEntities()` method is a getter method that returns the list of
    // entities. It returns an
    // `ArrayList` of type `Entity`, which contains all the entities created based
    // on the level data.
    // This method allows other classes to access and retrieve the list of entities
    // created in the
    // `FileScreen` class.
    public ArrayList<Entity> getEntities() {
        return entities;
    }
}