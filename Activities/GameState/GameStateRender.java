package com.game.bentz.ShapeShift.Activities.GameState;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.game.bentz.ShapeShift.GameLogic.CaptureZone;
import com.game.bentz.ShapeShift.GameLogic.GameStatus;
import com.game.bentz.ShapeShift.GameLogic.Player;
import com.game.bentz.ShapeShift.GameLogic.PowerUp.PowerUp;
import com.game.bentz.ShapeShift.GameLogic.Row;
import com.game.bentz.ShapeShift.GameLogic.Shape.Shape;
import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.CoolDownBar;
import com.game.bentz.ShapeShift.Utility.CriteriaTimeBar;
import com.game.bentz.ShapeShift.Utility.Notification;
import com.game.bentz.ShapeShift.Utility.NotificationCenter;
import com.game.bentz.ShapeShift.Utility.ShapeList;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Michael on 5/11/16.
 * A <code>GameStateRenderer</code> draws the current state of the objects
 * Draws the current state of the objects to the screen
 */
public class GameStateRender {
    private final String TAG = "GameStateRender";

    private CaptureZone mCaptureZone;
    private Row mRow;
    private GameStatus mGameStatus;
    private NotificationCenter mNotificationCenter;
    private Player mPlayer;
    private ShapeList mShapeList;
    private CoolDownBar mCoolDownBar;
    private Paint mPaint;

    /**
     * Constructor
     * @param mCaptureZone the <code>CaptureZone</code> where touch events are checked
     * @param gameStatus the <code>GameStatus</code> which holds the criteria, score and time left
     */
    public GameStateRender(ShapeList shapeList, CaptureZone mCaptureZone, Row row , GameStatus gameStatus, NotificationCenter notificationCenter){
        this.mCaptureZone = mCaptureZone;
        this.mRow = row;
        this.mGameStatus = gameStatus;
        this.mNotificationCenter = notificationCenter;
        this.mPlayer = Player.getInstance();
        this.mPaint = new Paint();
//        this.mShapeList = ShapeList.getInstance();
        this.mShapeList = shapeList;
        this.mCoolDownBar = CoolDownBar.getInstance();

        mPaint.setColor(Color.rgb(255, 255, 255));
        mPaint.setStrokeWidth(Constants.getPhysicalPx(2));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTypeface(Constants.DEFAULT_TYPEFACE);
        mPaint.setTextSize(Constants.DEFAULT_FONT_SIZE);
    }

    /**
     * Draws the objects and their current state
     * @param canvas the <code>Canvas</code> where the objects are drawn
     */
    public void draw(Canvas canvas){
        // Draw Row
        drawRow(canvas);
        // Draw the shapes
        drawShapes(canvas);
        // Draw the capture zone
        drawCaptureZone(canvas);
//         Draw the game status
        drawGameStatus(canvas);
//         Draw notifications
        drawNotifications(canvas);
        // Draw powerups
        drawPowerUps(canvas);
//         Draw cooldownbar
        drawCoolDownBar(canvas);
        // Draw timebar
        drawTimeBar(canvas);
    }

    private void drawRow(Canvas canvas){
        mRow.draw(canvas,mPaint);
    }


    /**
     * Draws the current position of the shapes onscreen
     * @param canvas the <code>Canvas</code> where the shapes are drawn
     */
    private void drawShapes(Canvas canvas){
        // For every shape currently onscreen
        for (Iterator<Shape> iterator = mShapeList.getOnScreenShapes().iterator(); iterator.hasNext();){
            Shape shape = iterator.next();
            // Draw the shape
            shape.draw(canvas,mPaint);
        }
    }

    /**
     * Draws the <code>CaptureZone</code>
     * @param canvas the <code>Canvas</code> where the <code>CaptureZone</code> is drawn
     */
    private void drawCaptureZone(Canvas canvas){
        // Draw the capture zone
//        canvas.drawRect(mCaptureZone.getBounds(), mPaint);
    }

    /**
     * Draws the <code>GameStatus</code>
     * @param canvas the <code>Canvas</code> where the <code>GameStatus</code> is drawn
     */
    private void drawGameStatus(Canvas canvas){
        mGameStatus.draw(canvas,mPaint);
    }

    private void drawNotifications(Canvas canvas){
        List<Notification> notificationList = mNotificationCenter.getNotifications();
        if (!notificationList.isEmpty()){
            for(Notification notification: notificationList){
                notification.draw(canvas,mPaint);
            }
        }
    }

    private void drawPowerUps(Canvas canvas){
        List<PowerUp> powerUpList = mPlayer.getPowerUps();
        if (!powerUpList.isEmpty()){
            for (PowerUp powerUp: powerUpList){
                powerUp.draw(canvas,mPaint);
            }
        }
    }

    private void drawCoolDownBar(Canvas canvas){
        mCoolDownBar.draw(canvas,mPaint);
    }

    private void drawTimeBar(Canvas canvas){
        CriteriaTimeBar.getInstance().draw(canvas,mPaint);
    }

//    /**
//     * Draws the given text onto the given bitmap
//     * @param bitmap the <code>Bitmap</code> to draw the text on
//     * @param gText the text to be written on the given <code>Bitmap</code>
//     * @return the <code>Bitmap</code> with the text
//     */
//    public Bitmap drawTextToBitmap(Bitmap bitmap,String gText){
//        float scale = Constants.DENSITY;
//        // resource bitmaps are imutable,
//        // so we need to convert it to mutable one
//        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//
//        Canvas canvas = new Canvas(bitmap);
////        // new antialised Paint
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        // text color - #3D3D3D
//        paint.setColor(Color.parseColor(mGameStatus.getColor()));
////        paint.setColor(Color.rgb(61, 61, 61));
//        // text size in pixels
//        paint.setTextSize((int) (14 * scale));
//        // text shadow
//        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
//
//        // draw text to the Canvas center
//        Rect bounds = new Rect();
//        paint.getTextBounds(gText, 0, gText.length(), bounds);
//        int x = (bitmap.getWidth() - bounds.width())/2;
//        int y = (bitmap.getHeight() + bounds.height())/2;
//
//        canvas.drawText(gText, x, y, paint);
//
//        return bitmap;
//    }
}
