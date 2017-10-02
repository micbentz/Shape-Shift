package com.game.bentz.ShapeShift.Utility;


/**
 * Created by Michael on 5/11/16.
 * This class holds the current position of an object.
 * TODO: Add more utility methods as needed
 */
public class Location{
    private int x;
    private int y;

    public Location(){}

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
