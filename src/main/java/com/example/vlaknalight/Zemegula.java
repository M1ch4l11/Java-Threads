package com.example.vlaknalight;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

public class Zemegula extends Canvas {
    ImageView viev;
    GraphicsContext gc;
    public Zemegula(){
        super(200,100);
        gc = getGraphicsContext2D();
        viev = new ImageView("Zemegula3.gif");
    }

    public void vykresli(){
        gc.clearRect(0,0,getWidth(),getHeight());
        gc.drawImage(viev.getImage(),0,0,getWidth(),getHeight());
    }

    public void vymaz(){
        gc.clearRect(0,0,getWidth(),getHeight());
    }
}
