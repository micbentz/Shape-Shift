package com.game.bentz.ShapeShift.GameLogic;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.Location;

/**
 * Created by Michael on 7/13/16.
 */
public class Row implements Drawable {
    private Location mLocation;
    private Speed mSpeed;

    public Row(){
        this.mLocation = new Location(0,0);
        this.mSpeed = new Speed(0,Constants.SPEED);
    }

    public Row(Location location, Speed speed){
        this.mLocation = location;
        this.mSpeed = speed;
    }

    public void reset(){
        mLocation.setX(0);
        mLocation.setY(0);
    }

    public Speed getSpeed(){
        return mSpeed;
    }

    public Location getLocation(){
        return mLocation;
    }

    public void setLocation(int x, int y){
        mLocation.setX(x);
        mLocation.setY(y);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawLine(0,mLocation.getY(),Constants.SCREEN_WIDTH,mLocation.getY() + 1f,paint);
    }
}
