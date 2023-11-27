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

    public void initEvents() {
        canvas.setOnKeyPressed(event -> {
            runScreens.onKeyPressed(event);
        });
        canvas.setOnKeyReleased(event -> {
            runScreens.onKeyReleased(event);
        });
    }

    private void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
        if (player.isPowerBombPlus())
            this.power2.setImage(power2);
        if (player.isPowerSpeed())
            this.power3.setImage(power3);
        if (player.isPowerFireFriends())
            this.power4.setImage(power4);

    }

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

    }

}
