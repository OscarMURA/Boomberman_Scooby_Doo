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

public class Player extends Avatar {

    private String name;
    private PlayerType type;
    private  int life;
    private ArrayList<Image> runRightImages;
    private ArrayList<Image> runLeftImages;
    private ImageView idleImage;
    private ArrayList<Image> walkRight;
    private ArrayList<Image> walkLeft;
    private ArrayList<Image> walkUp;
    private ArrayList<Image> walkDown;
    private int amountBombs;
    private long invensibilityStartTime;

    private long reloadBombStartTime;
    private boolean bomb;
    private boolean powerBomb;
    private boolean powerSpeed;
    private boolean powerFirePlus;
    private boolean powerFireFriends;
    private int level;


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
        life = 3;
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

    public void setCanva(Canvas canva) {
        this.canvas = canva;
        this.graphics = canvas.getGraphicsContext2D();
    }

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

    private void reloadBomb() {
        int maxBombs = 1;
        if (powerBomb) {
            maxBombs = 5;
        }
        if (System.currentTimeMillis() - reloadBombStartTime > 10000
                && amountBombs < maxBombs) {
            amountBombs++;
            reloadBombStartTime = System.currentTimeMillis();
        }
    }

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

    public int getIntensityOfExplosions() {
        int intesity = 1;
        if (powerFirePlus) {
            intesity = 4;
        }
        return intesity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public  int getLife() {
        return life;
    }

    public long getInvensibilityStartTime() {
        return invensibilityStartTime;
    }

    public void setInvensibilityStartTime() {
        this.invensibilityStartTime = System.currentTimeMillis();
    }
    public void lowerByOneLife() {
        this.life = life - 1;
    }

    public int getAmountBombs() {
        return amountBombs;
    }

    public void setAmountBombs(int amountBombs) {
        this.amountBombs = amountBombs;
    }


    public void onKeyPressed(KeyEvent event){
        switch (event.getCode()){
            case W, UP-> {

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

    public boolean putBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public PlayerType getType() {
        return type;
    }

    public boolean isPowerBombPlus() {
        return powerBomb;
    }

    public void setPowerBombPlus(boolean powerBomb) {
        this.powerBomb = powerBomb;
    }

    public boolean isPowerSpeed() {
        return powerSpeed;
    }

    public void setPowerSpeed(boolean powerSpeed) {
        this.powerSpeed = powerSpeed;
        if (speed >= 16) {
            walkRight = runRightImages;
            walkLeft = runLeftImages;
        }

    }

    public boolean isPowerFirePlus() {
        return powerFirePlus;
    }

    public void setPowerFirePlus(boolean powerFirePlus) {
        this.powerFirePlus = powerFirePlus;
    }

    public boolean isPowerFireFriends() {
        return powerFireFriends;
    }

    public void setPowerFireFriends(boolean powerFireFriends) {
        this.powerFireFriends = powerFireFriends;
    }

    protected boolean checkCollisions(int x, int y) {
        Entity entityToDestroy = null;
        boolean collision = true;
        for (Entity entity : entities) {
            if (this != entity && (collidesWithOtherEntity(entity, x, y))) {
                collision = false;
                if (entity instanceof Power) {
                    entityToDestroy = entity;
                    // collision = true;
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

    public void amountLifePlus() {
        if (life + 1 <= 3) {
            life++;
        }
    }

    private void increaseBombAmountByOne() {
        if (amountBombs + 1 <= 5) {
            amountBombs++;
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void nextLevel() {
        level++;
    }

    public int getLevel() {
        return level;
    }
}
