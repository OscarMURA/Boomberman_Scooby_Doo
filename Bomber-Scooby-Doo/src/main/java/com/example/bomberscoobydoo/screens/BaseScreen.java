package com.example.bomberscoobydoo.screens;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class BaseScreen  {

    protected Canvas canvas;
    protected GraphicsContext graphicsContext;


    public BaseScreen(Canvas canvas) {
        this.canvas = canvas;
        this.graphicsContext = canvas.getGraphicsContext2D();
    }

    public abstract void paint();




}
