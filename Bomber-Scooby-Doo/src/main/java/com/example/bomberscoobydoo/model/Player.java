package com.example.bomberscoobydoo.model;

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

public class Player extends Entity {

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
    private MoveType moveType;
    private int speed;
    int amountBombs;
    private int frame;
    private int intensityOfExplosions;
    private long invensibilityStartTime;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean moving;
    private boolean bomb;

    public Player( String name, PlayerType type) {
        super(null, new Vector(60,60), Destructible.DESTRUCTIBLE);
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
        intensityOfExplosions = 3;
        invensibilityStartTime = System.currentTimeMillis();
    }

    public void setCanva(Canvas canva){
        this.canvas = canva;
        this.graphics = canvas.getGraphicsContext2D();
    }

    public void initWalkRun(){
        int weight = 60;
        if(type==PlayerType.SHAGGY){
            weight = 35;
        }
        Image imageIdle = new Image(getClass().getResourceAsStream("/images/player/"+type+"/idle/idle-1.png"),weight,60,false,false);
        idleImage = new ImageView(imageIdle);
        for(int i = 1; i <= 9; i++) {
            Image image = new Image(getClass().getResourceAsStream("/images/player/"+type+"/walk/walk-0"+i+".png"),weight,60,false,false);
            walkRight.add(image);
            image = new Image(getClass().getResourceAsStream("/images/player/"+type+"/walk/left-0"+i+".png"),weight,60,false,false);
            walkLeft.add(image);

            Image imageRun = new Image(getClass().getResourceAsStream("/images/player/"+type+"/run/run-"+i+".png"),60,60,false,false);
            runRightImages.add(imageRun);
            imageRun = rotarImagen(imageRun,180);
            runLeftImages.add(imageRun);
        }
    }

    private void initUpDown(){
        int weight = 60;
        if(type==PlayerType.SHAGGY){
            weight = 35;
        }
        for(int i = 1; i <= 6; i++) {
            Image image = new Image(getClass().getResourceAsStream("/images/player/"+type+"/up/up-"+i+".png"),weight,55,false,false);
            ImageView imageView = new ImageView(image);
            walkUp.add(image);
            Image imageDown = new Image(getClass().getResourceAsStream("/images/player/"+type+"/down/down-"+i+".png"),weight,55,false,false);
            ImageView imageViewDown = new ImageView(imageDown);
            walkDown.add(imageDown);
        }
    }

    public void paint(){
        onMove();
        if(!moving){
            graphics.drawImage(idleImage.getImage(),position.getX(),position.getY());
        }
        if(moveType == UP){
            graphics.drawImage(walkUp.get(frame%6),position.getX(),position.getY());
            frame++;
        }
        if(moveType == DOWN){
            graphics.drawImage(walkDown.get(frame%6),position.getX(),position.getY());
            frame++;
        }
        if(moveType == LEFT){
            graphics.drawImage(walkLeft.get(frame%9),position.getX(),position.getY());
            frame++;
        }
        if(moveType == RIGHT){
            graphics.drawImage(walkRight.get(frame%9),position.getX(),position.getY());
            frame++;
        }
    }

    public void onMove(){
        collisionWithCanva();
        int directionX = 0;
        int directionY = 0;
            if (upPressed && !upCollision){
                directionY = -speed;
            }
            if (downPressed && !downCollision){
                directionY = speed;
            }
            if (leftPressed && !leftCollision){
                directionX = -speed;
            }
            if (rightPressed && !rightCollision){
                directionX = speed;
            }
            moveDirection(directionX,directionY);
    }

    public static Image rotarImagen(Image image, double grados) {
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
        // Definir el color de fondo que se considerará transparente (blanco en este caso)
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

    public int getIntensityOfExplosions(){
        return intensityOfExplosions;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public static int getLife() {
        return life;
    }
    public long getInvensibilityStartTime() {
        return invensibilityStartTime;
    }
    public void setInvensibilityStartTime() {
        this.invensibilityStartTime = System.currentTimeMillis();
    }
    public void lowerByOneLife(){
        life = life - 1;
    }
    public static void setLife(int life) {
        Player.life = life;
    }
    public int getAmountBombs() {
        return amountBombs;
    }
    public void setAmountBombs(int amountBombs) {
        this.amountBombs = amountBombs;
    }
    public PlayerType getType() {
        return type;
    }


    public void onKeyPressed(KeyEvent event){

        switch (event.getCode()){

            case W, UP-> {
                upPressed = true;
                moveType = UP;
            }
            case S, DOWN-> {
                downPressed = true;
                moveType = DOWN;
            }
            case LEFT, A->{
                leftPressed = true;
                moveType = LEFT;}
            case RIGHT, D->{
                rightPressed = true;
                moveType = RIGHT;}
            case SPACE,X->{
                bomb = true;}
        }
        if(upPressed || downPressed || leftPressed || rightPressed) {
            moving = true;
        }
    }

    public void onKeyReleased(KeyEvent event){
        switch(event.getCode()) {
            case W, UP -> upPressed = false;
            case S, DOWN -> downPressed = false;
            case LEFT, A -> leftPressed = false;
            case RIGHT, D -> rightPressed = false;
            case SPACE -> bomb = false;
        }
        if(!upPressed && !downPressed && !leftPressed && !rightPressed) {
            moving = false;
            moveType = STOP;
        }
    }
    public boolean putBomb() {
        return bomb;
    }
    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }
}
