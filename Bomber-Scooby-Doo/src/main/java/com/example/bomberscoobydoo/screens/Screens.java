package com.example.bomberscoobydoo.screens;

import com.example.bomberscoobydoo.effects.AudioManager;
import com.example.bomberscoobydoo.model.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.example.bomberscoobydoo.control.BomberGameControler;
import com.example.bomberscoobydoo.model.Door;
import com.example.bomberscoobydoo.model.Enemy;
import com.example.bomberscoobydoo.model.Entity;
import com.example.bomberscoobydoo.model.Player;
import com.example.bomberscoobydoo.model.Power;

public class Screens extends BaseScreen {

    private FileScreen fileScreen;
    private int currentLevel;
    private boolean doorTouched;
    private static final int MAX_EXPECTED_LEVEL = 3;

    public Screens(Canvas canvas) {
        super(canvas);
        background = new Image("floorClare.png");
        player = BomberGameControler.getInstance().getPlayer();
        this.fileScreen = new FileScreen();
        this.currentLevel = 0;
        imageLevel = new Image(getClass().getResourceAsStream("/images/Banner/level" + (currentLevel + 1) + ".png"));
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(imageLevel, 0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void nextLevel() {
        currentLevel++;
        Player.resetPlayer();
        loadLevel();
        fileScreen.createEntities(canvas);
        entities = fileScreen.getEntities();
        player.setPosition(new Vector(0, 0));
        player.setEntities(entities);
    }

    private void loadLevel() {
        System.out.println("Loading level " + currentLevel + "...");
        fileScreen.loadLevel(currentLevel);
    }

    @Override
    public void paint() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
        if (((Player) player).putBomb() && ((Player) (player)).getAmountBombs() > 0) {
            ((Player) player).setBomb(false);
            putBomb();
            // explodeBomb(5, new Vector(480, 480), MoveType.STOP);
        }
        checkExplosions();
        checkEnemyOverExplosion();

        if (!((Player) player).isPowerFireFriends())
            checkPlayerOverExplosion();
        for (Entity entity : entities) {
            if (entity instanceof Power) {
                entity.paint();
            }
        }
        for (Entity entity : entities) {
            if (!(entity instanceof Power)) {
                entity.paint();
            }
        }
        player.paint();

        if (((Player) player).getLevel() > currentLevel) {
            entities.clear();

            String imagePath = "/images/Banner/level" + (((Player) player).getLevel()) + ".png";
            System.out.println("Image Path: " + imagePath);
            System.out.println("level: " + ((Player) player).getLevel());
            imageLevel = new Image(getClass().getResourceAsStream(imagePath));
            BomberGameControler.getInstance().setTime(System.currentTimeMillis());
            if (System.currentTimeMillis() - BomberGameControler.getInstance().getTime() < 5000) {
                graphics.drawImage(imageLevel, 0, 0, canvas.getWidth(), canvas.getHeight());
            }
            AudioManager.getInstance().playMusic("/level" + ((Player) player).getLevel() + ".wav");
            nextLevel();
        }
        if (System.currentTimeMillis() - BomberGameControler.getInstance().getTime() < 5000) {
            graphics.drawImage(imageLevel, 0, 0, canvas.getWidth(), canvas.getHeight());
        }

    }

}