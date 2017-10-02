package com.game.bentz.ShapeShift.GameLogic;

/**
 * Created by Michael on 5/18/16.
 */
public class Speed {
    private static final String TAG = "Speed";

    private double x;
    private double y;

    public Speed(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }


    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}
