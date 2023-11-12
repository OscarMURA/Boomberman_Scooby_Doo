package com.example.bomberscoobydoo.control;

import com.example.bomberscoobydoo.PlayGame;
import com.example.bomberscoobydoo.effects.AudioManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGame implements Initializable {

    @FXML
    private Label title;
    
    @FXML
    private TextField name;

    @FXML
    private Button start;
    @FXML
    private RadioButton radio1;
    @FXML
    private RadioButton radio2;

    private AudioManager audio = AudioManager.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InputStream is1 = getClass().getResourceAsStream("/fonts/scoobydoo.ttf");
        Font customFont = Font.loadFont(is1, 14);
        // Aplicar la fuente a todos los controles
        title.setFont(customFont);
        name.setFont(customFont);
        radio1.setFont(customFont);
        radio2.setFont(customFont);
        start.setFont(customFont);
        audio.playEffectInBackground("/scooby.wav");
        audio.setMusicPath("/singScoobydoo.wav");
        audio.playMusic(1000);

    }

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
