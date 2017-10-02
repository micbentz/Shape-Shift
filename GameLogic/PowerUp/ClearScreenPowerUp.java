package com.game.bentz.ShapeShift.GameLogic.PowerUp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.game.bentz.ShapeShift.Activities.GameState.GameState;
import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.GraphicLoader;
import com.game.bentz.ShapeShift.Utility.Location;
import com.game.bentz.ShapeShift.Utility.Notification;

/**
 * Created by Michael on 7/13/16.
 */
public class ClearScreenPowerUp extends PowerUp {
    private static final Location mLocation = new Location(Constants.COL_2 - Constants.BITMAP_WIDTH/2,Constants.SCREEN_END);
    private static final Notification mNotification  = new Notification.NotificationBuilder("CLEAR SCREEN")
            .location(new Location(Constants.SCREEN_WIDTH/4,Constants.HEIGHT_NOTIFICATIONS))
            .time(Notification.SHORT)
            .direction(Notification.STATIONARY)
            .build();
    private static final Bitmap image = GraphicLoader.clearScreenPowerUp;

    public ClearScreenPowerUp(int time, int charges){
        super(mLocation,mNotification,time,charges);
    }

    @Override
    public void activate(){
        super.activate();
        if (isActive()){
            // Apply Effect
            GameState.getInstance().removeShapes();
        }
    }

    @Override
    public void deactivate(){
        super.deactivate();
        // Remove the effect
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
