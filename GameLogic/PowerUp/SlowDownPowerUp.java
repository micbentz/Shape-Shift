package com.game.bentz.ShapeShift.GameLogic.PowerUp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.GraphicLoader;
import com.game.bentz.ShapeShift.Utility.Location;
import com.game.bentz.ShapeShift.Utility.Notification;

/**
 * Created by Michael on 6/30/16.
 */
public class SlowDownPowerUp extends PowerUp {
    private static final Location mLocation = new Location(Constants.COL_1 - Constants.BITMAP_WIDTH/2,Constants.SCREEN_END);
    private static final Notification mNotification = new Notification.NotificationBuilder("SLOW DOWN")
//            .location(new Location(Constants.SCREEN_WIDTH/4,Constants.SCREEN_HEIGHT/2))
            .location(new Location(Constants.SCREEN_WIDTH/4,Constants.HEIGHT_NOTIFICATIONS))
            .time(Notification.SHORT)
            .direction(Notification.STATIONARY)
            .build();
    private static final Bitmap image = GraphicLoader.slowDownPowerUp;

    private int modifier;

    public SlowDownPowerUp(int time, int charges){
        super(mLocation,mNotification,time,charges);
        this.modifier = 2;
    }

    public SlowDownPowerUp(Rect bounds, int time, Notification notification, int modifier){
        super(bounds, time,notification);
        this.modifier = modifier;
    }

    @Override
    public void activate(){
        super.activate();
        if (isActive()){
            // Apply the modifier
            Constants.SPEED_MODIFIER /= modifier;
        }
    }

    @Override
    public void deactivate(){
        super.deactivate();
        // Reset modifier
        Constants.SPEED_MODIFIER = Constants.CURRENT_SPEED_MODIFIER;
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        if (!depleted()){
//            Paint localPaint = new Paint(paint);
//            localPaint.setColor(Color.MAGENTA);
//            canvas.drawRect(getBounds(),localPaint);
            canvas.drawBitmap(image,getLocation().getX(),getLocation().getY(),paint);
        }
    }
}
