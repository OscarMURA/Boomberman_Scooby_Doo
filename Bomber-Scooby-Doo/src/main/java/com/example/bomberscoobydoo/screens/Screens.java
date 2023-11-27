package com.example.bomberscoobydoo.screens;

import com.example.bomberscoobydoo.effects.AudioManager;
import com.example.bomberscoobydoo.model.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import com.example.bomberscoobydoo.control.BomberGameControler;
import com.example.bomberscoobydoo.model.Entity;
import com.example.bomberscoobydoo.model.Player;
import com.example.bomberscoobydoo.model.Power;

/**
 * The "Screens" class extends the "BaseScreen" class.
 */
public class Screens extends BaseScreen {
    
    private FileScreen fileScreen;
    private int currentLevel;

    // The `public Screens(Canvas canvas)` constructor is initializing a new instance of the `Screens`
    // class. It takes a `Canvas` object as a parameter and calls the constructor of the superclass
    // `BaseScreen` with the same parameter.
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

    /**
     * The function "nextLevel" increases the current level, resets the player, loads the level,
     * creates entities on the file screen, sets the player's position to (0, 0), and sets the player's
     * entities.
     */
    public void nextLevel() {
        currentLevel++;
        Player.resetPlayer();
        loadLevel();
        fileScreen.createEntities(canvas);
        entities = fileScreen.getEntities();
        player.setPosition(new Vector(0, 0));
        player.setEntities(entities);
    }

    /**
     * The function "loadLevel" prints a message indicating the current level being loaded and then
     * calls the "loadLevel" method of the "fileScreen" object, passing in the current level as a
     * parameter.
     */
    private void loadLevel() {
        System.out.println("Loading level " + currentLevel + "...");
        fileScreen.loadLevel(currentLevel);
    }

    /**
     * This function is responsible for painting the game screen, including the background, bombs,
     * explosions, enemies, power-ups, and the player character.
     */
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
            //AudioManager.getInstance().playEffect()
            entities.clear();
            //Solo se puede subir de nivel hasta el 3
            if(((Player) player).getLevel()<4){
                imageLevel = new Image(
                        getClass().getResourceAsStream("/images/Banner/level" + (((Player) player).getLevel()) + ".png"));
                BomberGameControler.getInstance().setTime(System.currentTimeMillis());
                graphics.drawImage(imageLevel, 0, 0, canvas.getWidth(), canvas.getHeight());
                AudioManager.getInstance().playEffect("/levelUp.wav");
                AudioManager.getInstance().playMusic("/level" + ((Player) player).getLevel() + ".wav");
                nextLevel();
            }
        }
        if (System.currentTimeMillis() - BomberGameControler.getInstance().getTime() < 5000) {
            graphics.drawImage(imageLevel, 0, 0, canvas.getWidth(), canvas.getHeight());
        }

    }

}