package com.game.bentz.ShapeShift.GameLogic.Shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.game.bentz.ShapeShift.GameLogic.Drawable;
import com.game.bentz.ShapeShift.GameLogic.Interactable;
import com.game.bentz.ShapeShift.GameLogic.Speed;
import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.GraphicLoader;
import com.game.bentz.ShapeShift.Utility.Key;
import com.game.bentz.ShapeShift.Utility.Location;

/**
 * Created by Michael on 5/4/16.
 * A <code>Shape</code> is an abstract class
 * Generic shape class. Shapes will flow down the screen and have a specific shape and color.
 * TODO: add getArea methods to check for onTouch events, remove isTouched
 */
public class Shape implements Interactable, Drawable {
    private static final String TAG = "Shape";

    private Classification mClassification;
    private Rect bounds;
    private Location mLocation;
    private Speed speed;
    private Bitmap image;
    private float scalePercentage = .75f;
    private int scaleDecrement;
    private float scaleIncrement;

    public Shape(Classification classification){
        this.mClassification = classification;
        this.speed = new Speed(0, Constants.SPEED);
        this.mLocation = new Location();
//        this.bounds = Constants.BITMAP_BOUNDS;
        // TODO update to constant
        this.bounds = new Rect(0,0,GraphicLoader.imageHashMap.get(new Key(0,0)).getWidth(),GraphicLoader.imageHashMap.get(new Key(0,0)).getHeight() + GraphicLoader.imageHashMap.get(new Key(0,0)).getHeight() * 1/10);
//        this.bounds = new Rect(0,0,GraphicLoader.imageMap.get(0).getWidth(),GraphicLoader.imageMap.get(0).getHeight() + GraphicLoader.imageMap.get(0).getHeight() * 1/10);
//        this.bounds = new Rect(0,0,GraphicLoader.hashMap.get(new Pair<>(0,0)).getWidth(),GraphicLoader.hashMap.get(new Pair<>(0,0)).getHeight() + GraphicLoader.hashMap.get(new Pair<>(0,0)).getHeight() * 1/10);
        // Get the classification
        Key key = new Key(mClassification.getCategory().getValue(),mClassification.getPattern().getValue());
        // Get the image
        this.image = GraphicLoader.imageHashMap.get(key);
        // TODO replace with Constant time value
        this.scaleIncrement = .0025f; // Will replace with Constant time
        this.scaleDecrement = Constants.SCALING_SIZE -1;
    }

    public void reset(){
        scaleDecrement = Constants.SCALING_SIZE - 1;
    }

    public Classification getClassification(){
        return mClassification;
    }

    public void setLocation(int x, int y){
        mLocation.setX(x);
        mLocation.setY(y);
    }

    public Location getLocation(){
        return mLocation;
    }

    public void setSpeed(double x, double y){
        this.speed.setX(x);
        this.speed.setY(y);
    }

    public Speed getSpeed(){
        return this.speed;
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        // Get the classification
        Key key = new Key(mClassification.getCategory().getValue(),mClassification.getPattern().getValue());
        // Draw the bitmap with the given classification
//        canvas.drawBitmap(GraphicLoader.imageHashMap.get(key),getLocation().getX(),getLocation().getY(),paint);
        Bitmap bitmap = GraphicLoader.imagesHashMap.get(key)[scaleDecrement];
        canvas.drawBitmap(bitmap,getLocation().getX(),getLocation().getY(),paint);



        // Most Recent
//        if (scalePercentage < 1){
//            canvas.drawBitmap(scaleBitmap(image),getLocation().getX(),getLocation().getY(),paint);
//        } else{
//            canvas.drawBitmap(image,getLocation().getX(),getLocation().getY(),paint);
//        }



//        canvas.drawBitmap(image,getLocation().getX(),getLocation().getY(),paint);
    }

    @Override
    public Rect getBounds(){
        return this.bounds;
    }

    @Override
    public void updateBounds(int x, int y){
        bounds.offsetTo(x,y);
    }

    @Override
    public boolean checkBounds(int x, int y){
        if (bounds.contains(x,y)){
            return true;
        }
        return false;
    }

    public void decrementScale(){
        if (scaleDecrement > 0){
            scaleDecrement--;
        }
    }

    public void incrementScale(){
        if (scalePercentage <= 1){
            scalePercentage += scaleIncrement;
        }
    }

    public void resetScale(){
        scalePercentage = .70f;
        scaleDecrement = Constants.SCALING_SIZE - 1;
    }


    /**
     * Unecessary functions from Decorator Pattern
     *
     */
//    public void setColor(String color){
//        this.mColor = color;
//    }
//    public String getColor(){
//        return mColor;
//    }
//    public String getType(){
//        return type;
//    }


}
