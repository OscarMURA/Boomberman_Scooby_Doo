package com.example.bomberscoobydoo.control;

import com.example.bomberscoobydoo.PlayGame;
import com.example.bomberscoobydoo.effects.AudioManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * The ControllerGame class is responsible for initializing the game.
 */
public class ControllerGame implements Initializable {

    @FXML
    private Label title;

    @FXML
    private TextField name;
    @FXML
    ImageView level1;

    @FXML
    private Button start;
    @FXML
    private RadioButton radio1;
    @FXML
    private RadioButton radio2;

    /**
     * The initialize function loads a custom font, applies it to various controls, plays sound effects, and plays random music.
     * 
     * @param location The location parameter i s the URL of the FXML file that is being initialized. It represents the location of the FXML file on the file system or in a JAR file.
     * @param resources The `resources` parameter in the `initialize` method is a `ResourceBundle` object that contains the resources for the specified `location`. It is typically used to access localized resources such as strings, images, or other assets.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InputStream is1 = getClass().getResourceAsStream("/fonts/scoobydoo.ttf");
        Font customFont = Font.loadFont(is1, 15);
        // Aplicar la fuente a todos los controles
        title.setFont(customFont);
        name.setFont(customFont);
        radio1.setFont(customFont);
        radio2.setFont(customFont);
        start.setFont(customFont);
        AudioManager.getInstance().playEffect("/scooby.wav");
        Random random = new Random();
        int num = random.nextInt(2)+1;
        AudioManager.getInstance().playMusic("/sing"+num+".wav");
    }

    /**
     * The function "onActionStart" is used to handle the start of a game by creating a player with a specified name and character type, and then opening the game maps window.
     */
    /**
     * The function "onActionStart" is used to handle the start of a game by creating a player with a specified name and character type, and then opening the game maps window.
     */
    public void onActionStart(){
        Stage stage = (Stage) start.getScene().getWindow();
        BomberGameControler bomber = BomberGameControler.getInstance();
        String name= this.name.getText();
        boolean allow=true;
        String type="";
        if(radio1.isSelected())
            type = "Scooby Doo";
        else if(radio2.isSelected())
            type = "Shaggy";

        if(radio1.isSelected() && radio2.isSelected()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select only one character", ButtonType.OK);
            alert.showAndWait();
            allow=false;
        }
        if(name.equals("") || type.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all the fields", ButtonType.OK);
            alert.showAndWait();
            allow=false;
        }

        if(allow){
            bomber.createPlayer(name,type);
            PlayGame.openWindow("GameMaps.fxml");
            stage.close();
        }
    }


}
