package com.game.bentz.ShapeShift.Utility;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.game.bentz.ShapeShift.GameLogic.Drawable;

/**
 * Created by Michael on 7/14/16.
 */
public class CriteriaTimeBar implements Drawable, Timed {
    private static CriteriaTimeBar instance;

    private Integer time;
    private float decrement;
    private float height;
    private boolean isActive;

    private CriteriaTimeBar(){
        this.height = 0;
        this.isActive = false;
    }

    public synchronized static CriteriaTimeBar getInstance(){
        if (instance == null){
            instance = new CriteriaTimeBar();
        }
        return instance;
    }

    public boolean isActive(){
        return  isActive;
    }

    public void start(int time){
        this.time = time;
//        decrement = (Constants.SCREEN_WIDTH / time)  * Constants.ANIMATION_TIMER;
        decrement = ((Constants.SCREEN_WIDTH / Constants.CRITERIA_MODIFIER)) * Constants.ANIMATION_TIMER;
    }

    public void reset(){
        height = 0;
        isActive = false;
        decrement = ((Constants.SCREEN_WIDTH / Constants.CRITERIA_MODIFIER)) * Constants.ANIMATION_TIMER;
    }


    @Override
    public void draw(Canvas canvas, Paint paint) {
        Paint localPaint = new Paint(paint);
        localPaint.setColor(Color.argb(150,255,0,0));
        localPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,Constants.HEIGHT_TIMER_BAR,Constants.SCREEN_WIDTH - height,Constants.HEIGHT_TOP_BAR,localPaint);
    }

    @Override
    public Integer getTimer() {
        return time;
    }

    @Override
    public void decreaseTime(int time) {

    }

    @Override
    public void decreaseTime() {
        if (!Constants.KEEP_CRITERIA){
            height += decrement;
        }
    }
}
