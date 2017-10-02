package com.game.bentz.ShapeShift.Utility;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.game.bentz.ShapeShift.GameLogic.Drawable;

/**
 * Created by Michael on 7/13/16.
 */
public class CoolDownBar implements Drawable, Timed{
    private static CoolDownBar instance;

    private float decrement;
    private float height;
    private Integer time;
    private boolean isActive;

    private CoolDownBar(){
        this.height = 0;
        this.isActive = false;
    }

    public synchronized static CoolDownBar getInstance(){
        if (instance == null){
            instance = new CoolDownBar();
        }
        return instance;
    }


    public boolean isActive(){
        return isActive;
    }

    public void start(int time){
        isActive = true;
        this.time = time;
        decrement = ((Constants.SCREEN_HEIGHT - Constants.SCREEN_END) / time) * Constants.ANIMATION_TIMER;
    }

    public void reset(){
        height = 0;
        isActive = false;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (isActive){
            Paint localPaint = new Paint(paint);
            localPaint.setColor(Color.argb(200,255,0,0));
            localPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0,Constants.SCREEN_END + height,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT,localPaint);
        }
    }

    @Override
    public Integer getTimer() {
        return time;
    }

    @Override
    public void decreaseTime(int time) {
        this.time -= time;
    }

    @Override
    public void decreaseTime() {
        height += decrement;
    }
}
