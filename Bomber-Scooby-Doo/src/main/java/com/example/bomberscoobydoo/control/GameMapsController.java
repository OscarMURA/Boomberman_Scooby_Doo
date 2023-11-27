package com.example.bomberscoobydoo.control;

import com.example.bomberscoobydoo.PlayGame;
import com.example.bomberscoobydoo.effects.AudioManager;
import com.example.bomberscoobydoo.model.Player;
import com.example.bomberscoobydoo.model.PlayerType;
import com.example.bomberscoobydoo.screens.BaseScreen;
import com.example.bomberscoobydoo.screens.Screens;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The GameMapsController class is responsible for initializing game maps.
 */
public class GameMapsController implements Initializable {

    @FXML
    private Canvas canvas;
    private BomberGameControler bomber;
    @FXML
    ImageView banner;
    @FXML
    Label player;
    @FXML
    Label name;
    @FXML
    Label life;
    @FXML
    ImageView life1;
    @FXML
    ImageView life2;
    @FXML
    ImageView life3;
    @FXML
    ImageView bom1;
    @FXML
    ImageView bom2;
    @FXML
    ImageView bom3;
    @FXML
    ImageView bom4;
    @FXML
    ImageView bom5;
    @FXML
    Label bombs;
    @FXML
    Label power;
    @FXML
    ImageView skin;
    @FXML
    ImageView power1;
    @FXML
    ImageView power2;
    @FXML
    ImageView power3;
    @FXML
    ImageView power4;
    private BaseScreen runScreens;
    private boolean gameOverWindowShown;
    private boolean gameWinWindowShown;
    private boolean isRunning;

    /**
     * This function initializes the JavaFX application by setting up the canvas, starting a new thread to continuously update the game state, and initializing event handlers.
     * 
     * @param location The location of the FXML file that defines the layout of the scene.
     * @param resources The `resources` parameter is a `ResourceBundle` object that contains the resources for the current locale. It is used to retrieve localized strings, images, and other resources that are needed for the application.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvas.setFocusTraversable(true);
        isRunning = true;
        bomber = BomberGameControler.getInstance();
        bomber.getPlayer().setCanva(this.canvas);
        runScreens = new Screens(canvas);
        gameOverWindowShown = false;
        gameWinWindowShown = false;
        initFonts();
        new Thread(() -> {
            while (isRunning) {
                Platform.runLater(() -> {
                    showResource();
                    runScreens.paint();
                    gameOverOrWin();
                });
                pause(50);
            }
        }).start();
        initEvents();
    }

    
    /**
     * The function initializes fonts and sets them for various elements in a Java application, including the player's name, life, bombs, power, and skin image.
     */
    public void initFonts() {

        InputStream is1 = getClass().getResourceAsStream("/fonts/scoobydoo.ttf");
        Font customFont = Font.loadFont(is1, 26);
        player.setFont(customFont);
        name.setFont(customFont);
        name.setText(bomber.getPlayer().getName());
        life.setFont(customFont);
        bombs.setFont(customFont);
        power.setFont(customFont);
        if (bomber.getPlayer().getType() == PlayerType.SCOOBYDOO) {
            Image image = new Image(getClass().getResourceAsStream("/images/Banner/skin1.png"));
            skin.setImage(image);
        } else {
            Image image = new Image(getClass().getResourceAsStream("/images/Banner/skin2.png"));
            skin.setImage(image);
        }

    }

    /**
     * The function "initEvents" sets up event listeners for key presses and releases on a canvas, and calls corresponding methods in the "runScreens" object.
     */
    public void initEvents() {
        canvas.setOnKeyPressed(event -> {
            runScreens.onKeyPressed(event);
        });
        canvas.setOnKeyReleased(event -> {
            runScreens.onKeyReleased(event);
        });
    }

    /**
     * The pause function pauses the execution of the program for a specified amount of time.
     * 
     * @param time The "time" parameter is an integer value representing the number of milliseconds to pause the execution of the current thread.
     */
    private void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   /**
    * The function `showResource()` sets the images of various resources (life, bombs, power-ups) based on the player's current state.
    */
    public void showResource() {
        Player player = bomber.getPlayer();
        Image life = new Image(getClass().getResourceAsStream("/images/Banner/life.png"));
        Image bomb = new Image(getClass().getResourceAsStream("/images/Banner/bombs.png"));
        Image noLife = new Image(getClass().getResourceAsStream("/images/Banner/noLife.png"));
        Image noBomb = new Image(getClass().getResourceAsStream("/images/Banner/noBombs.png"));
        Image power1 = new Image(getClass().getResourceAsStream("/images/Banner/FIRE_PLUS.png"));
        Image power2 = new Image(getClass().getResourceAsStream("/images/Banner/BOMB_PLUS.png"));
        Image power3 = new Image(getClass().getResourceAsStream("/images/Banner/SPEED.png"));
        Image power4 = new Image(getClass().getResourceAsStream("/images/Banner/FIRE_FRIEND.png"));
        if (player.getLife() == 3) {
            life3.setImage(life);
            life2.setImage(life);
            life1.setImage(life);
        } else if (player.getLife() == 2) {
            life3.setImage(noLife);
            life2.setImage(life);
            life1.setImage(life);
        } else if (player.getLife() == 1) {
            life3.setImage(noLife);
            life2.setImage(noLife);
            life1.setImage(life);
        }
        if (player.getAmountBombs() == 5) {
            bom1.setImage(bomb);
            bom2.setImage(bomb);
            bom3.setImage(bomb);
            bom4.setImage(bomb);
            bom5.setImage(bomb);
        } else if (player.getAmountBombs() == 4) {
            bom1.setImage(bomb);
            bom2.setImage(bomb);
            bom3.setImage(bomb);
            bom4.setImage(bomb);
            bom5.setImage(noBomb);
        } else if (player.getAmountBombs() == 3) {
            bom1.setImage(bomb);
            bom2.setImage(bomb);
            bom3.setImage(bomb);
            bom4.setImage(noBomb);
            bom5.setImage(noBomb);
        } else if (player.getAmountBombs() == 2) {
            bom1.setImage(bomb);
            bom2.setImage(bomb);
            bom3.setImage(noBomb);
            bom4.setImage(noBomb);
            bom5.setImage(noBomb);
        } else if (player.getAmountBombs() == 1) {
            bom1.setImage(bomb);
            bom2.setImage(noBomb);
            bom3.setImage(noBomb);
            bom4.setImage(noBomb);
            bom5.setImage(noBomb);
        } else if (player.getAmountBombs() == 0) {
            bom1.setImage(noBomb);
            bom2.setImage(noBomb);
            bom3.setImage(noBomb);
            bom4.setImage(noBomb);
            bom5.setImage(noBomb);
        }
        if (player.isPowerFirePlus())
            this.power1.setImage(power1);
        else
            this.power1.setImage(null);
        if (player.isPowerBombPlus())
            this.power2.setImage(power2);
        else
            this.power2.setImage(null);
        if (player.isPowerSpeed())
            this.power3.setImage(power3);
        else
            this.power3.setImage(null);
        if (player.isPowerFireFriends())
            this.power4.setImage(power4);
        else
            this.power4.setImage(null);

    }

    /**
     * The function checks if the player has either lost all their lives or reached level 4, and if so, it displays the corresponding game over or win window.
     */
    private void gameOverOrWin(){
        String path="";
        Player player = bomber.getPlayer();
        if((player.getLife() <= 0 && !gameOverWindowShown) || (player.getLevel() > 3 && !gameWinWindowShown)){
            if(player.getLife() <= 0 && !gameOverWindowShown){
                path = "Over";
                gameOverWindowShown = true;
            }else if(player.getLevel() >  3 && !gameWinWindowShown){
                path = "Win";
                gameWinWindowShown = true;
            }
            if(path != ""){
                gameOverOrWin(path);
            }
        }
    }

    /**
     * The function "gameOverOrWin" plays a video based on the given path and opens a new window with the video playing, while closing the current window.
     * 
     * @param path The "path" parameter is a string that represents the path to the video file that will be played in the game over or win screen. It is used to construct the URL of the video file using the getResource() method.
     */
    private void gameOverOrWin(String path) {

            Stage stage = (Stage) name.getScene().getWindow();
            AudioManager.getInstance().stopMusic();
            Media media = new Media(getClass().getResource("/videos/Game_"+path+".mp4").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
            // Crear la escena y agregar el visor de medios
            StackPane root = new StackPane();
            root.getChildren().add(mediaView);
            Scene scene = new Scene(root, 800, 500);
            // Configurar el escenario
            Image image = new Image(PlayGame.class.getResourceAsStream("/icon/iconBomber.png"));
            Stage game = new Stage();
            game.getIcons().add(image);
            game.setScene(scene);
            game.setTitle("Game "+path);
            game.show();
            //Play
            mediaPlayer.play();
            // Manejar el cierre de la ventana del video
            game.setOnCloseRequest(windowEvent -> {PlayGame.openWindow("hello-view.fxml");});
            isRunning = false;
            stage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game "+path);
        alert.setHeaderText("Game "+path+" finished "+name.getText());
        alert.setContentText("Game "+path+" finished "+name.getText());
        alert.showAndWait();

    }

}
