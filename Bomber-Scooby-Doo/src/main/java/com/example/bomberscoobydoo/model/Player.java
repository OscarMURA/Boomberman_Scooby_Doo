package com.example.bomberscoobydoo.model;

import com.example.bomberscoobydoo.effects.ControlUser;
import com.example.bomberscoobydoo.effects.MoveType;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

import java.util.ArrayList;


import static com.example.bomberscoobydoo.effects.MoveType.STOP;
import static com.example.bomberscoobydoo.effects.MoveType.UP;
import static com.example.bomberscoobydoo.effects.MoveType.DOWN;
import static com.example.bomberscoobydoo.effects.MoveType.LEFT;
import static com.example.bomberscoobydoo.effects.MoveType.RIGHT;

public class Player extends Entity {

    private ControlUser control;
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
        this.control = ControlUser.getInstance();
        life = 3;
        moveType = STOP;
        amountBombs = 1;
        speed = 10;
        initWalkRun();
        initUpDown();
    }

    public void setCanva(Canvas canva){
        this.canvas = canva;
        this.graphics = canvas.getGraphicsContext2D();
    }

    public void initWalkRun(){
        Image imageIdle = new Image(getClass().getResourceAsStream("/images/player/"+type+"/idle/idle-1.png"),60,60,false,false);
        idleImage = new ImageView(imageIdle);
        for(int i = 1; i <= 9; i++) {
            Rotate rotate = new Rotate(180,0,0);
            System.out.println("i: "+i);
            Image image = new Image(getClass().getResourceAsStream("/images/player/"+type+"/walk/walk-0"+i+".png"),60,60,false,false);
            walkRight.add(image);
            image = new Image(getClass().getResourceAsStream("/images/player/"+type+"/walk/left-0"+i+".png"),60,60,false,false);
            walkLeft.add(image);

            Image imageRun = new Image(getClass().getResourceAsStream("/images/player/"+type+"/run/run-"+i+".png"),60,60,false,false);
            runRightImages.add(imageRun);
            imageRun = rotarImagen(imageRun,180);
            runLeftImages.add(imageRun);
        }
    }

    private void initUpDown(){
        for(int i = 1; i <= 6; i++) {
            Image image = new Image(getClass().getResourceAsStream("/images/player/"+type+"/up/up-"+i+".png"),55,55,false,false);
            ImageView imageView = new ImageView(image);
            walkUp.add(image);
            Image imageDown = new Image(getClass().getResourceAsStream("/images/player/"+type+"/down/down-"+i+".png"),55,55,false,false);
            ImageView imageViewDown = new ImageView(imageDown);
            walkDown.add(imageDown);
        }
    }

    public void paint(){
        onMove();
        if(!control.move){

            graphics.drawImage(idleImage.getImage(),position.getX(),position.getY());
        }
        if(control.moveType == UP){
            graphics.drawImage(walkUp.get(frame%6),position.getX(),position.getY());
            frame++;
        }
        if(control.moveType == DOWN){
            graphics.drawImage(walkDown.get(frame%6),position.getX(),position.getY());
            frame++;
        }
        if(control.moveType == LEFT){
            graphics.drawImage(walkLeft.get(frame%9),position.getX(),position.getY());
            frame++;
        }
        if(control.moveType == RIGHT){
            graphics.drawImage(walkRight.get(frame%9),position.getX(),position.getY());
            frame++;
        }
    }

    public void onMove(){
        collisionWithCanva();
        int directionX = 0;
        int directionY = 0;
        if(control.move ){
            if (control.up && !upCollision){
                directionY = -speed;
            }
            if (control.down && !downCollision){
                directionY = speed;
            }
            if (control.left && !leftCollision){
                directionX = -speed;
            }
            if (control.right && !rightCollision){
                directionX = speed;
            }
            moveDirection(directionX,directionY);
        }

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
        return writableImage;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public static int getLife() {
        return life;
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
}
