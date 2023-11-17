package com.example.bomberscoobydoo.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Powers extends Entity{

    PowersType type;
    Image image;
    public Powers(Canvas canvas, Vector position,String type) {
        super(canvas, position, Destructible.DESTRUCTIBLE);
        type = type.toUpperCase();
        if(type.equals("FIRE_PLUS")){
            this.type = PowersType.FIRE_PLUS;
        }else if(type.equals("BOMB_PLUS")){
            this.type = PowersType.BOMB_PLUS;
        }else if(type.equals("SPEED")){
            this.type = PowersType.SPEED;
        }else if(type.equals("FIRE_FRIEND")){
            this.type = PowersType.FIRE_FRIEND;
        }
        image = new Image(getClass().getResourceAsStream("/images/Banner/"+this.type+".png"), 50, 50, false, false);
    }

    @Override
    public void paint() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.drawImage(image, position.getX(), position.getY());
    }

    public PowersType getType() {
        return type;
    }
}
