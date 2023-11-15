package com.example.bomberscoobydoo;

import com.example.bomberscoobydoo.effects.AudioManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class PlayGame extends Application {

    private static AudioManager audio = AudioManager.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        openWindow("hello-view.fxml");
    }

    public static void openWindow(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(PlayGame.class.getResource(fxml));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            Image image = new Image(PlayGame.class.getResourceAsStream("/icon/iconBomber.png"));
            Stage stage = new Stage();
            stage.getIcons().add(image);
            stage.setTitle("Boomber Scooby Doo!!!");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) scene.getWindow();
        stage.setOnCloseRequest(windowEvent -> {Platform.exit();System.exit(0);});
    }
    public static void main(String[] args) {
        launch(args);
    }

}