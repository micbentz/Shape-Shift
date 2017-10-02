package com.game.bentz.ShapeShift.Utility;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.game.bentz.ShapeShift.GameLogic.Drawable;

import java.util.Random;

/**
 * Created by Michael on 6/30/16.
 * A <code>Notification</code> is a customizable pop-up message
 */
public class Notification implements Drawable, Timed {
    private final String TAG = "Notification";

    public static final int SHORT = 1;
    public static final int MEDIUM = 3;
    public static final int LONG = 5;

    public static final int STATIONARY = 0;
    public static final int UP_RIGHT = 1;
    public static final int UP_LEFT = -1;

//    public static final int DEFAULT_HEIGHT = Constants.SCREEN_HEIGHT * 1/7;

    private String text;
    private int time;
    private Location mLocation;
    private int direction;
    private float alpha;
    private float alphaDecrement;

    /**
     * Constructor takes in <code>NotificationBuilder</code>
     */
    public Notification(NotificationBuilder notificationBuilder){
        this.text = notificationBuilder.text;
        this.time = notificationBuilder.time;
        this.mLocation = notificationBuilder.mLocation;
        this.direction = notificationBuilder.direction;
        this.alpha = 255;
        this.alphaDecrement = (alpha / this.time) * Constants.ANIMATION_TIMER;
    }

    /**
     * Constructor
     * @param text the <monocode>text</monocode> the notification will display
     * @param time the amount of <monocode>time</monocode> the <code>Notification</code> will be displayed
     */
    public Notification(String text, int time){
        this.text = text;
        this.time = time;
        this.mLocation = new Location();
        this.alpha = 255;
        this.alphaDecrement = alpha / (this.time * 10);
//        this.direction = generateDirection();
    }

    /**
     * Constructor
     * @param text the <monocode>text</monocode> the notification will display
     * @param time the amount of <monocode>time</monocode> the <code>Notification</code> will be displayed
     * @param location the starting <code>Location</code> of the <code>Notification</code>
     */
    public Notification(String text, int time, Location location){
        this.text = text;
        this.time = time;
        this.mLocation = location;
        this.alpha = 255;
        this.alphaDecrement = alpha / (this.time * 10);
//        this.direction = generateDirection();
    }


    /**
     * Returns the text of the <code>Notification</code>
     * @return the <monocode>text</monocode> of the <code>Notification</code>
     */
    public String getText(){
        return this.text;
    }

    /**
     * Returns the amount time that the <code>Notification</code> will display
     * @return the amount of <monocode>time</monocode> the <code>Notification</code> will be displayed
     */
    public Integer getTimer(){
        return time;
    }

    @Override
    public void decreaseTime(int x) {
        time -= x;
    }

    @Override
    public void decreaseTime(){
        time--;
    }


    /**
     * Returns the direction of the <code>Notification</code>
     * @return the <monocode>direction</monocode> the notification will travel
     */
    public int getDirection(){
//        Log.d(TAG,"direction: " + direction);
        return this.direction;
    }


    /**
     * Returns the location of the <code>Notification</code>
     * @return the <code>Location</code>
     */
    public Location getLocation(){
        return mLocation;
    }

    /**
     * Sets the location of the <code>Notification</code>
     * @param x the <monocode>X</monocode> value of the new location
     * @param y the <monocode>Y</monocode> value of the new location
     */
    public void setLocation(int x, int y){
        mLocation.setX(x);
        mLocation.setY(y);
    }

    /**
     * Decrements the alpha value of the notification.
     * <br> Used as a fading effect
     */
    public void decrementAlpha(){
        this.alpha -= alphaDecrement;
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        Paint localPaint = new Paint(paint);
        localPaint.setColor(Color.argb(0 + (int)alpha,100,0,255));
        canvas.drawText(text,mLocation.getX(),mLocation.getY(),localPaint);
    }

    public static class NotificationBuilder{
        private String text;
        private int time;
        private int direction;
        private Location mLocation;

        public NotificationBuilder(String text){
            this.text = text;
            this.direction = generateDirection();
        }

        public NotificationBuilder text(String text){
            this.text = text;
            return this;
        }

        public NotificationBuilder time(int time){
            this.time = time;
            return this;
        }

        public NotificationBuilder location(Location location){
            this.mLocation = location;
            return this;
        }

        public NotificationBuilder direction(int direction){
            this.direction = direction;
            return this;
        }

        public Notification build(){
            Notification notification = new Notification(this);
            validateUserObject();
            return notification;
        }

        private void validateUserObject(){

        }

        private int generateDirection(){
            Random random = new Random();
            return random.nextInt(3) - 1;
        }
    }
}
