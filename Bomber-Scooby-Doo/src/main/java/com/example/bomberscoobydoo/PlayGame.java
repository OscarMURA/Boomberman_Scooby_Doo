package com.example.bomberscoobydoo;

import com.example.bomberscoobydoo.effects.AudioManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The PlayGame class is a Java application.
 */
public class PlayGame extends Application {

// The line `private static AudioManager audio = AudioManager.getInstance();` is creating a static
// instance of the `AudioManager` class and assigning it to the `audio` variable. The
// `AudioManager.getInstance()` method is a static method that returns a singleton instance of the
// `AudioManager` class. This allows other parts of the code to access the `AudioManager` instance and
// use its methods and properties.
    private static AudioManager audio = AudioManager.getInstance();

    /**
     * The start function opens a window using the "hello-view.fxml" file.
     * 
     * @param stage The stage parameter represents the main window of the JavaFX application. It is the
     * top-level container for all JavaFX content.
     */
    @Override
    public void start(Stage stage) throws IOException {
        openWindow("hello-view.fxml");
    }

    /**
     * The function "openWindow" opens a new window using JavaFX, loads an FXML file, sets the scene,
     * and displays the window with a specified title and icon.
     * 
     * @param fxml The parameter "fxml" is a String that represents the path to the FXML file that will
     * be loaded and displayed in the new window.
     */
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