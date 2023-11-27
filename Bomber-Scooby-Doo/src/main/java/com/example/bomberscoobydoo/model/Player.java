package com.example.bomberscoobydoo.model;

import com.example.bomberscoobydoo.effects.AudioManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import java.util.ArrayList;
import static com.example.bomberscoobydoo.model.MoveType.STOP;
import static com.example.bomberscoobydoo.model.MoveType.UP;
import static com.example.bomberscoobydoo.model.MoveType.DOWN;
import static com.example.bomberscoobydoo.model.MoveType.LEFT;
import static com.example.bomberscoobydoo.model.MoveType.RIGHT;

/**
 * The Player class is a subclass of the Avatar class.
 */
public class Player extends Avatar {

    private String name;
    private PlayerType type;
    private static int life;
    private ArrayList<Image> runRightImages;
    private ArrayList<Image> runLeftImages;
    private ImageView idleImage;
    private ArrayList<Image> walkRight;
    private ArrayList<Image> walkLeft;
    private ArrayList<Image> walkUp;
    private ArrayList<Image> walkDown;
    private static int amountBombs;
    private long invensibilityStartTime;
    private long time;

    private long reloadBombStartTime;
    private  boolean bomb;
    private  boolean powerBomb;
    private  boolean powerSpeed;
    private  boolean powerFirePlus;
    private  boolean powerFireFriends;
    private int level;

    // The above code is defining a constructor for a class called Player. The constructor takes in two
    // parameters, a name and a PlayerType. It initializes various instance variables and sets their
    // initial values. It also calls two methods, initWalkRun() and initUpDown(), to initialize some
    // other variables.
    public Player(String name, PlayerType type) {
        super(null, new Vector(1, 1), Destructible.INDESTRUCTIBLE);
        runRightImages = new ArrayList<>();
        runLeftImages = new ArrayList<>();
        walkRight = new ArrayList<>();
        walkLeft = new ArrayList<>();
        walkUp = new ArrayList<>();
        walkDown = new ArrayList<>();
        this.name = name;
        this.type = type;
        time = System.currentTimeMillis();
        life =3;
        moveType = STOP;
        amountBombs = 1;
        speed = 10;
        initWalkRun();
        initUpDown();
        invensibilityStartTime = System.currentTimeMillis();
        powerBomb = false;
        powerSpeed = false;
        powerFirePlus = false;
        powerFireFriends = false;
        level = 1;
        reloadBombStartTime = System.currentTimeMillis();
    }

    /**
     * The function sets the canvas and graphics context for a Java application.
     * 
     * @param canva The "canva" parameter is of type Canvas. It is used to set the canvas object that
     * will be used for drawing graphics.
     */
    public void setCanva(Canvas canva) {
        this.canvas = canva;
        this.graphics = canvas.getGraphicsContext2D();
    }

   /**
    * The function "resetLife" sets the value of the variable "life" to 3.
    */
    public static void resetLife() {
        life = 3;
    }

    /**
     * The function "resetPlayer" resets various player attributes to their default values.
     */
    public  void resetPlayer() {
        life = 3;
        amountBombs = 1;
        powerBomb = false;
        powerSpeed = false;
        powerFirePlus = false;
        powerFireFriends = false;
    }
    /**
     * The function initializes the images for the player's idle, walk, and run animations based on the
     * player's type and weight.
     */
    public void initWalkRun() {
        int weight = 60;
        if (type == PlayerType.SHAGGY) {
            weight = 35;
        }
        Image imageIdle = new Image(getClass().getResourceAsStream("/images/player/" + type + "/idle/idle-1.png"),
                weight, 60, false, false);
        idleImage = new ImageView(imageIdle);
        for (int i = 1; i <= 9; i++) {
            Image image = new Image(
                    getClass().getResourceAsStream("/images/player/" + type + "/walk/walk-0" + i + ".png"), weight, 60,
                    false, false);
            walkRight.add(image);
            image = new Image(getClass().getResourceAsStream("/images/player/" + type + "/walk/left-0" + i + ".png"),
                    weight, 60, false, false);
            walkLeft.add(image);

            Image imageRun = new Image(
                    getClass().getResourceAsStream("/images/player/" + type + "/run/run-" + i + ".png"), 60, 60, false,
                    false);
            runRightImages.add(imageRun);
            imageRun = rotateImage(imageRun, 180);
            runLeftImages.add(imageRun);
        }
    }

   /**
    * The function initializes the up and down animations for a player object, based on the player's
    * type.
    */
    private void initUpDown() {
        int weight = 60;
        if (type == PlayerType.SHAGGY) {
            weight = 35;
        }
        for (int i = 1; i <= 6; i++) {
            Image image = new Image(getClass().getResourceAsStream("/images/player/" + type + "/up/up-" + i + ".png"),
                    weight, 55, false, false);
            ImageView imageView = new ImageView(image);
            walkUp.add(image);
            Image imageDown = new Image(
                    getClass().getResourceAsStream("/images/player/" + type + "/down/down-" + i + ".png"), weight, 55,
                    false, false);
            ImageView imageViewDown = new ImageView(imageDown);
            walkDown.add(imageDown);
        }
    }

    /**
     * The function reloadBomb() checks if enough time has passed and if the maximum number of bombs
     * has not been reached, then it increments the amount of bombs and updates the reload bomb start
     * time.
     *
     * If the player has the power of bombs it reloads faster.
     */
    private void reloadBomb() {
        int maxBombs = 1;
        if (powerBomb) {
            maxBombs = 5;
        }

        if((powerBomb && System.currentTimeMillis() - reloadBombStartTime > 5000
                && amountBombs < maxBombs) || System.currentTimeMillis() - reloadBombStartTime > 10000){
            amountBombs++;
            reloadBombStartTime = System.currentTimeMillis();
        }
    }

    /**
     * The paint() function is responsible for drawing the character on the screen based on its current
     * movement type and frame.
     */
    public void paint() {
        onMove();
        reloadBomb();

        if (!isMoving) {
            graphics.drawImage(idleImage.getImage(), position.getX(), position.getY());
        }
        if (moveType == UP) {
            graphics.drawImage(walkUp.get(frame % 6), position.getX(), position.getY());
            frame++;
        }
        if (moveType == DOWN) {
            graphics.drawImage(walkDown.get(frame % 6), position.getX(), position.getY());
            frame++;
        }
        if (moveType == LEFT) {
            graphics.drawImage(walkLeft.get(frame % 9), position.getX(), position.getY());
            frame++;
        }
        if (moveType == RIGHT) {
            graphics.drawImage(walkRight.get(frame % 9), position.getX(), position.getY());
            frame++;
        }
    }

    /**
     * The function rotates an image by a specified number of degrees and returns the rotated image.
     * 
     * @param image The original image that you want to rotate.
     * @param grados The "grados" parameter represents the angle in degrees by which the image should
     * be rotated.
     * @return The method returns a rotated and reflected image.
     */
    public static Image rotateImage(Image image, double grados) {
        ImageView imageView = new ImageView(image);

        // Aplicar una transformación de escala para reflejar horizontalmente (espejo)
        Scale scale = new Scale(-1, 1, 0, 0);
        imageView.getTransforms().add(scale);

        // Crear una nueva imagen a partir del ImageView con la reflexión aplicada
        Image imagenReflejada = imageView.snapshot(null, null);

        // Crear una WritableImage a partir de la imagen reflejada
        int width = (int) imagenReflejada.getWidth();
        int height = (int) imagenReflejada.getHeight();
        WritableImage writableImage = new WritableImage(width, height);

        PixelReader pixelReader = imagenReflejada.getPixelReader();
        javafx.scene.image.PixelWriter pixelWriter = writableImage.getPixelWriter();
        // Definir el color de fondo que se considerará transparente (blanco en este
        // caso)
        Color fondoTransparente = Color.WHITE;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pixelReader.getColor(x, y);
                if (!color.equals(fondoTransparente)) {
                    pixelWriter.setColor(x, y, color);
                }
            }
        }
        return writableImage;
    }

    /**
     * The function returns the intensity of explosions, with a default value of 1 and a higher value
     * of 4 if the powerFirePlus variable is true.
     * 
     * @return The method is returning the intensity of explosions, which is an integer value.
     */
    public int getIntensityOfExplosions() {
        int intesity = 1;
        if (powerFirePlus) {
            intesity = 4;
        }
        return intesity;
    }

    /**
     * The getName() function returns the name of an object.
     * 
     * @return The method is returning the value of the variable "name".
     */
    public String getName() {
        return name;
    }

    /**
     * The function sets the name of an object.
     * 
     * @param name The name parameter is a String that represents the name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The function returns the value of the variable "life".
     * 
     * @return The method is returning the value of the variable "life".
     */
    public int getLife() {
        return life;
    }

   /**
    * The function returns the start time of invincibility.
    * 
    * @return The method is returning the value of the variable "invensibilityStartTime", which is of
    * type long.
    */
    public long getInvensibilityStartTime() {
        return invensibilityStartTime;
    }

    /**
     * The function sets the invincibility start time to the current system time in milliseconds.
     */
    public void setInvensibilityStartTime() {
        this.invensibilityStartTime = System.currentTimeMillis();
    }

   /**
    * The function "lowerByOneLife" decreases the value of the variable "life" by 1.
    */
    public void lowerByOneLife() {
        this.life = life - 1;
    }

    /**
     * The function returns the amount of bombs.
     * 
     * @return The method is returning the value of the variable "amountBombs".
     */
    public int getAmountBombs() {
        return amountBombs;
    }

    /**
     * The function sets the amount of bombs to a specified value.
     * 
     * @param amountBombs The amount of bombs to be set.
     */
    public void setAmountBombs(int amountBombs) {
        this.amountBombs = amountBombs;
    }

    /**
     * The function handles key presses and sets corresponding boolean variables and moveType based on
     * the pressed key.
     * 
     * @param event The parameter "event" is of type KeyEvent, which represents a key event that
     * occurred.
     */
    public void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case W, UP -> {

                goUp = true;
                moveType = UP;
            }
            case S, DOWN -> {
                goDown = true;
                moveType = DOWN;
            }
            case LEFT, A -> {
                goLeft = true;
                moveType = LEFT;
            }
            case RIGHT, D -> {
                goRight = true;
                moveType = RIGHT;
            }
            case SPACE, X -> {
                bomb = true;
            }
        }
        if (goUp || goDown || goLeft || goRight) {
            isMoving = true;
        }
    }

    /**
     * The function handles key releases and updates the corresponding boolean variables based on the
     * released key.
     * 
     * @param event The parameter "event" is of type KeyEvent. It represents the key event that
     * occurred, such as a key being released.
     */
    public void onKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W, UP -> goUp = false;
            case S, DOWN -> goDown = false;
            case LEFT, A -> goLeft = false;
            case RIGHT, D -> goRight = false;
            case SPACE, X -> bomb = false;
        }
        if (!goUp && !goDown && !goLeft && !goRight) {
            isMoving = false;
            moveType = STOP;
        }
    }

    /**
     * The function "putBomb" returns a boolean value indicating whether a bomb was placed.
     * 
     * @return The method is returning a boolean value, which indicates whether a bomb has been placed
     * or not.
     */
    public boolean putBomb() {
        return bomb;
    }

   /**
    * The function sets the value of the "bomb" variable.
    * 
    * @param bomb A boolean value indicating whether or not there is a bomb.
    */
    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    /**
     * The function returns the type of the player.
     * 
     * @return The method is returning the value of the variable "type" of type PlayerType.
     */
    public PlayerType getType() {
        return type;
    }

    /**
     * The function isPowerBombPlus returns a boolean value indicating whether the powerBomb is true or
     * false.
     * 
     * @return The method is returning a boolean value, specifically the value of the variable
     * "powerBomb".
     */
    public boolean isPowerBombPlus() {
        return powerBomb;
    }

    /**
     * The function sets the value of the powerBomb variable.
     * 
     * @param powerBomb A boolean value indicating whether the power bomb is enabled or not.
     */
    public void setPowerBombPlus(boolean powerBomb) {
        this.powerBomb = powerBomb;
    }

    /**
     * The function returns a boolean value indicating whether power speed is enabled or not.
     * 
     * @return The method is returning a boolean value.
     */
    public boolean isPowerSpeed() {
        return powerSpeed;
    }

    /**
     * The function sets the power speed and updates the walking images if the speed is greater than or
     * equal to 10.
     * 
     * @param powerSpeed The powerSpeed parameter is a boolean value that determines whether the power
     * speed is activated or not.
     */
    public void setPowerSpeed(boolean powerSpeed) {
        this.powerSpeed = powerSpeed;
        if (speed >= 10) {
            walkRight = runRightImages;
            walkLeft = runLeftImages;
        }

    }

    /**
     * The function returns a boolean value indicating whether the powerFirePlus variable is true or
     * false.
     * 
     * @return The method is returning a boolean value, specifically the value of the variable
     * powerFirePlus.
     */
    public boolean isPowerFirePlus() {
        return powerFirePlus;
    }

    /**
     * The function sets the value of the powerFirePlus variable.
     * 
     * @param powerFirePlus A boolean variable that represents whether the power of fire is increased
     * or not.
     */
    public void setPowerFirePlus(boolean powerFirePlus) {
        this.powerFirePlus = powerFirePlus;
    }

    /**
     * The function returns a boolean value indicating whether powerFireFriends is true or false.
     * 
     * @return The method is returning a boolean value, specifically the value of the variable
     * powerFireFriends.
     */
    public boolean isPowerFireFriends() {
        return powerFireFriends;
    }

    /**
     * The function sets the value of the powerFireFriends variable.
     * 
     * @param powerFireFriends The parameter "powerFireFriends" is a boolean variable that represents
     * whether the power to fire friends is enabled or not.
     */
    public void setPowerFireFriends(boolean powerFireFriends) {
        this.powerFireFriends = powerFireFriends;
    }

    /**
     * The function checks for collisions between the player and other entities, and performs specific
     * actions based on the type of entity collided with.
     * 
     * @param x The x-coordinate of the entity's position.
     * @param y The parameter `y` represents the y-coordinate of the entity's position.
     * @return The method is returning a boolean value, which indicates whether there is a collision or
     * not.
     */
    protected boolean checkCollisions(int x, int y) {
        Entity entityToDestroy = null;
        boolean collision = true;
        boolean doorCollision = false; 
        for (Entity entity : entities) {
            if (this != entity && (collidesWithOtherEntity(entity, x, y))) {
                collision = false;
                if (entity instanceof Power) {
                    entityToDestroy = entity;
                    // collision = true;
                }
                if (entity instanceof Door) {
                    if(System.currentTimeMillis()!=time) {
                        if (!enemiesPresent()){
                            nextLevel();
                            Vector position = new Vector(0, 0);
                            this.setPosition(position);
                            time = System.currentTimeMillis();
                        }
                    }

                }
            }
        }

    
        if (entityToDestroy != null) {
            AudioManager.getInstance().playEffect("/item.wav");
            PowersType type = ((Power) entityToDestroy).getType();
            if (type == PowersType.BOMB_PLUS) {
                setPowerBombPlus(true);
                amountBombs = 5;
            }
            if (type == PowersType.FIRE_FRIEND) {
                setPowerFireFriends(true);
                increaseBombAmountByOne();
            }
            if (type == PowersType.FIRE_PLUS) {
                setPowerFirePlus(true);
                increaseBombAmountByOne();
            }
            if (type == PowersType.SPEED) {
                setSpeed(speed + 4);
                increaseBombAmountByOne();
                setPowerSpeed(true);
            }
            if (type == PowersType.LIFE_PLUS) {
                amountLifePlus();

            }
            collision = !collision;
            entities.remove(entityToDestroy);
        }
        return collision;
    }

    /**
     * The function checks if there are any enemies present in a list of entities.
     * 
     * @return The method is returning a boolean value. It returns true if there is at least one enemy
     * present in the "entities" list, and false otherwise.
     */
    private boolean enemiesPresent() {
        for (Entity entity : entities) {
            if (entity instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    /**
     * The function increases the value of the variable "life" by 1, but only if the resulting value is
     * less than or equal to 3.
     */
    public void amountLifePlus() {
        if (life + 1 <= 3) {
            life++;
        }
    }

    /**
     * The function increases the amount of bombs by one, as long as the total amount does not exceed
     * 5.
     */
    private void increaseBombAmountByOne() {
        if (amountBombs + 1 <= 5) {
            amountBombs++;
        }
    }

    /**
     * The function sets the speed of an object.
     * 
     * @param speed The "speed" parameter is an integer value that represents the speed of an object.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * The function "nextLevel" increases the level, resets the player, and sets the reload bomb start
     * time.
     */
    public void nextLevel() {
        System.out.println("nivel actual: " + level);
        level++;
        System.out.println("nivel siguiente: " + level);
        resetPlayer();
        reloadBombStartTime = System.currentTimeMillis();
    }

    /**
     * The function returns the value of the level variable.
     * 
     * @return The method is returning the value of the variable "level".
     */
    public int getLevel() {
        return level;
    }

    /**
     * The function sets the level of an object.
     * 
     * @param level The level parameter is an integer that represents the level of something.
     */
    public void setLevel(int level) {
        this.level = level;
    }

}
