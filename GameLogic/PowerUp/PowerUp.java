package com.game.bentz.ShapeShift.GameLogic.PowerUp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.game.bentz.ShapeShift.GameLogic.Drawable;
import com.game.bentz.ShapeShift.GameLogic.Interactable;
import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.CoolDownBar;
import com.game.bentz.ShapeShift.Utility.GlobalCoolDown;
import com.game.bentz.ShapeShift.Utility.Location;
import com.game.bentz.ShapeShift.Utility.Notification;
import com.game.bentz.ShapeShift.Utility.Timed;

/**
 * Created by Michael on 6/23/16.
 * A <code>PowerUp</code> is a one-time use effect
 */
public abstract class PowerUp implements Drawable, Interactable, Timed {
    public static final int INSTANT = 1;

    private boolean isActive;
    private int time;
    private int timer;
    private Integer charges;
    private Rect bounds;
    private Location mLocation;
    private GlobalCoolDown mGlobalCoolDown;
    private Notification mNotification;

    public PowerUp(Location location, Notification notification, int time, int charges){
        this.isActive = false;
        this.mLocation = location;
        this.mNotification = notification;
        this.time = time;
        this.timer = time;
        this.charges = charges;
        this.mGlobalCoolDown = GlobalCoolDown.getInstance();
        // TODO Address temporary fix
//        this.bounds = new Rect(0,0,location.getX(),location.getY());
        this.bounds = new Rect(0,0, Constants.BITMAP_WIDTH,Constants.BITMAP_WIDTH);
        this.bounds.offsetTo(location.getX(),location.getY());
    }

    public PowerUp(Rect bounds, int time, int charges){
        this.isActive = false;
        this.bounds = bounds;
        this.time = time;
        this.timer = time;
        this.charges = charges;
        this.mGlobalCoolDown = GlobalCoolDown.getInstance();
    }

    public PowerUp(Rect bounds, int timer, Notification notification){
        this.bounds = bounds;
        this.timer = timer;
        this.mNotification = notification;
        this.mGlobalCoolDown = GlobalCoolDown.getInstance();
    }

    public Notification getNotification(){
        return mNotification;
    }

    protected boolean depleted() {
        return charges == 0;
    }

    protected Location getLocation(){
        return mLocation;
    }

    public boolean isActive(){
        return isActive;
    }

    public void activate(){
        // If there are charges left and no powerup is active
        if (!depleted()){
            // set active to true
            isActive = true;
            // start the global cooldown
            mGlobalCoolDown.startCountdown();
        }
    }

    protected void deactivate(){
        // Set inactive
        isActive = false;
        // Reset the timer
        timer = time;
        // Remove a charge
        if (charges > 0){
            charges--;
        }
        // Reset the global cooldown
        mGlobalCoolDown.resetCountdown();
    }

    public Integer getTimer(){
        return timer;
    }

    @Override
    public void decreaseTime(int x){
        timer -= x;
    }

    @Override
    public void decreaseTime(){
        timer--;
        if (timer == 0 ){
            // TODO Consider moving to GameStateController
            deactivate();
            CoolDownBar.getInstance().reset();
        }
    }

    @Override
    public void updateBounds(int x, int y) {
        bounds.offsetTo(x,y);
    }

    @Override
    public Rect getBounds() {
        return bounds;
    }

    @Override
    public boolean checkBounds(int x, int y) {
        if (bounds.contains(x,y)){
            return true;
        }
        return false;
    }

    @Override
    public abstract void draw(Canvas canvas, Paint paint);
}
