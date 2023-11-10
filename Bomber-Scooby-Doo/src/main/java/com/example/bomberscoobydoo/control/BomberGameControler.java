package com.example.bomberscoobydoo.control;

import com.example.bomberscoobydoo.model.Player;
import com.example.bomberscoobydoo.model.Vector;
import com.example.bomberscoobydoo.model.PlayerType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class BomberGameControler {
    private Player player;

    private static BomberGameControler instance;
    public static BomberGameControler getInstance() {
        if(instance == null) {
            instance = new BomberGameControler();
        }
        return instance;
    }

    private BomberGameControler() {
    }

    public boolean createPlayer( String name,String type) {
        if(type.equals("Scooby Doo"))
            player = new Player(name, PlayerType.SCOOBYDOO);
        else
            player = new Player(name, PlayerType.SHAGGY);
        return true;
    }

    public Player getPlayer() {
        return player;
    }
}
