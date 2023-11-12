package com.example.bomberscoobydoo.effects;
import javafx.scene.Scene;

public class ControlUser {

    private static ControlUser instance;
    private ControlUser() {
    }
    public static ControlUser getInstance() {
        if(instance == null) {
            instance = new ControlUser();
        }
        return instance;
    }

    public static boolean move;
    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static boolean bomb;
    public static MoveType moveType;

    public void commands(Scene scene) {

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case S, DOWN -> {down = true;
                    moveType = MoveType.DOWN;
                }
                case W, UP -> {up = true;
                    moveType = MoveType.UP;
                }
                case A, LEFT ->{ left = true;
                    moveType = MoveType.LEFT;
                }
                case D, RIGHT ->{ right = true;
                    moveType = MoveType.RIGHT;}

                case X, SPACE ->{ bomb = false;}
            }
            if(up || down || left || right) {
                move = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W, UP -> up = false;
                case S, DOWN -> down = false;
                case A, LEFT -> left = false;
                case D, RIGHT -> right = false;
                case X, SPACE -> bomb = true;
            }
            if(!up && !down && !left && !right) {
                move = false;
                moveType = MoveType.STOP;
            }
        });

    }

}
