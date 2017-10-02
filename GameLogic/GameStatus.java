package com.game.bentz.ShapeShift.GameLogic;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.game.bentz.ShapeShift.GameLogic.Criteria.Criteria;
import com.game.bentz.ShapeShift.GameLogic.Shape.Classification;
import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.GraphicLoader;
import com.game.bentz.ShapeShift.Utility.Key;

/**
 * Created by Michael on 5/18/16.
 * TODO add String difficulty to constructor
 */
public class GameStatus implements Drawable{
    private Integer score;
    private Integer lives = 3;
    private Integer timeLeft;
    private CriteriaList mCriteriaList;

    public GameStatus(String difficulty){
        this.score = 0;
        this.timeLeft = Constants.PLAY_TIME;
        this.mCriteriaList = new CriteriaList("HARD");
    }

    public void reset(){
        this.score = 0;
        this.lives = 3;
        this.timeLeft = Constants.PLAY_TIME;
    }

    public Criteria getCurrentCriteria(){
        return mCriteriaList.getCurrentCriteria();
    }

    public void generateNewCriteria(){
        mCriteriaList.generateNewCriteria();
    }

    public Integer getScore(){
        return score;
    }

    public Integer getTimeLeft(){
        return this.timeLeft;
    }

    public Integer getLivesLeft(){ return lives;}

    public void updateTime(){
        this.timeLeft--;
    }

    public void increaseTime(){
        timeLeft += Constants.TIME_INCREASE;
    }

    public void decreaseTime(){
        // Check that stops the timeleft from going below 0
        if (timeLeft - Constants.TIME_DECREASE <= 0){
            timeLeft = 0;
        } else {
            timeLeft -= Constants.TIME_DECREASE;
        }
    }

    public void incrementScore(){
        score += 1 * Constants.POINT_MODIFIER;
    }

    public void incrementScore(int x){
        score += score * x;
    }

    public void incrementScore(int x, int multiplier){
        score += x * multiplier;
    }

    public void loseLife(){
        if (lives != 0){
            lives--;
        }
    }

    public void loseGame(){
        this.lives = 0;
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        Paint newPaint = new Paint(paint);
        newPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0,Constants.SCREEN_WIDTH,Constants.HEIGHT_TOP_BAR,newPaint);
        canvas.drawRect(0,Constants.SCREEN_END,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT,newPaint);

        /**
         * Draw the current Criteria
         */
        // Get the classification
        Classification classification = mCriteriaList.getClassification(mCriteriaList.getCurrentCriteria());
        // Get the key
        Key key = new Key(classification.getCategory().getValue(),classification.getPattern().getValue());
        // Get the correct Image
        Bitmap bitmap = GraphicLoader.imageHashMap.get(key);
        // Scale the bitmap
//        bitmap = Bitmap.createScaledBitmap(bitmap,Constants.BITMAP_WIDTH/2,Constants.BITMAP_WIDTH/2,false);
        // Get the width
        int bWidth = bitmap.getWidth();
        // Draw the bitmap
        canvas.drawBitmap(bitmap,Constants.SCREEN_WIDTH/2 - bitmap.getWidth()/2, Constants.HEIGHT_CURRENT_CRITERIA,paint);


        /**
         * Draw the next criteria
         */
        // Get the classification
        classification = mCriteriaList.getClassification(mCriteriaList.getNextCriteria());
        // Get the key
        key = new Key(classification.getCategory().getValue(),classification.getPattern().getValue());
        // Get the correct Image
        bitmap = GraphicLoader.imageHashMap.get(key);
        // Scale the bitmap
        bitmap = Bitmap.createScaledBitmap(bitmap,Constants.BITMAP_WIDTH/2,Constants.BITMAP_WIDTH/2,false);
        // Get the width
        int lWidth = bitmap.getWidth();
        // Draw the bitmap
        canvas.drawBitmap(bitmap,Constants.X_NEXT_CRITERIA, Constants.HEIGHT_GAME_STATUS - bitmap.getWidth()/2,paint);
//        Constants.HEIGHT_CURRENT_CRITERIA + bitmap.getWidth()/2

//        canvas.drawBitmap(bitmap,Constants.SCREEN_WIDTH * 2/15 - (lWidth + lWidth/2) , Constants.HEIGHT_CURRENT_CRITERIA ,paint);
//        int start =Constants.SCREEN_WIDTH * 2/15 - (lWidth + lWidth/2);
//        int end = Constants.SCREEN_WIDTH * 2/15 - lWidth;
//        float floatX = Constants.SCREEN_WIDTH * 2/15 - (lWidth + lWidth/2)/2;
//        float floatY = (Constants.SCREEN_WIDTH /20) + (bWidth-lWidth)/2;

        /**
         * Draws text
         */
        Paint localPaint = new Paint(paint);

        localPaint.setStyle(Paint.Style.FILL);
        localPaint.setColor(Color.BLACK);
//        canvas.drawText("NEXT",floatX - (localPaint.getTextSize() + localPaint.getTextSize()/2),floatY,localPaint);


//        canvas.drawText(timeLeft.toString(), Constants.SCREEN_WIDTH * 9/10, paint.getTextSize(),localPaint);
        /**
         * Draw the lives left
         */
//        canvas.drawText(lives.toString(),Constants.SCREEN_WIDTH * 9/10 - localPaint.getTextSize(), localPaint.getTextSize(),localPaint);
        canvas.drawText(lives.toString(), Constants.getPhysicalPx(324) - localPaint.measureText(lives.toString())/2, Constants.HEIGHT_GAME_STATUS + localPaint.measureText(lives.toString())/2 ,localPaint);
//        canvas.drawText(lives.toString(), Constants.SCREEN_WIDTH - localPaint.getTextSize()/2, Constants.HEIGHT_GAME_STATUS ,localPaint);
//        Constants.HEIGHT_CURRENT_CRITERIA + localPaint.getTextSize() * 2
        /**
         * Draw the score
         */
        localPaint.setTextSize(Constants.getPhysicalPx(90));
        localPaint.setColor(Color.WHITE);
//        localPaint.get
        canvas.drawText(score.toString(), Constants.SCREEN_WIDTH/2  - localPaint.measureText(score.toString())/2, Constants.SCREEN_HEIGHT/2 ,localPaint);
//        canvas.drawText(score.toString(), Constants.getPhysicalPx(288) - localPaint.getTextSize(), Constants.HEIGHT_CURRENT_CRITERIA + localPaint.getTextSize()/2 ,localPaint);
    }

}
