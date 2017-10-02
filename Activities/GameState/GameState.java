package com.game.bentz.ShapeShift.Activities.GameState;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.game.bentz.ShapeShift.GameLogic.CaptureZone;
import com.game.bentz.ShapeShift.GameLogic.GameStatus;
import com.game.bentz.ShapeShift.GameLogic.Player;
import com.game.bentz.ShapeShift.GameLogic.PowerUp.PowerUp;
import com.game.bentz.ShapeShift.GameLogic.Row;
import com.game.bentz.ShapeShift.GameLogic.Shape.Shape;
import com.game.bentz.ShapeShift.Utility.Buffer;
import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.CoolDownBar;
import com.game.bentz.ShapeShift.Utility.CriteriaTimeBar;
import com.game.bentz.ShapeShift.Utility.GlobalCoolDown;
import com.game.bentz.ShapeShift.Utility.Location;
import com.game.bentz.ShapeShift.Utility.Notification;
import com.game.bentz.ShapeShift.Utility.NotificationCenter;
import com.game.bentz.ShapeShift.Utility.Pool;
import com.game.bentz.ShapeShift.Utility.ShapeList;

import java.util.List;
import java.util.Random;

/**
 * Created by Michael on 5/10/16.
 * A <code>GameState</code> handles the game events such as starting, ending, and touch.
 * It holds the <code>GameStateController</code> and <code>GameStateRenderer</code> calling the
 * <code>update()</code> and <code>render()</code> respectively
 */
public class GameState {
    private static final String TAG = "GameState";
    private static GameState instance;

    private static CaptureZone mCaptureZone;
    private static GameStatus mGameStatus;
    private static NotificationCenter mNotificationCenter;
    private static GlobalCoolDown mGlobalCoolDown;
    private static Player mPlayer;
    private static Row mRow;
    private static ShapeList mShapeList;
    private static Context mContext;
    private static Pool mPool;
    private static GameStateController mGameStateController;
    private static GameStateRender mGameStateRender;
    private static boolean gameOver = false;
    private static int count = 0;


    private GameState(Context context){
        init(context);
    }


    public synchronized static GameState getInstance(Context context){
        if (instance == null){
            instance = new GameState(context);
        }
        return instance;
    }

    public synchronized static GameState getInstance(){
        if (instance == null){
            instance = new GameState(mContext);
        }
        return instance;
    }
    /**
     * Initializes all the classes needed to play the game
     * @param context the <code>Context</code> to send a Broadcast
     */
    private void init(Context context){
        mCaptureZone =  new CaptureZone();
        mGameStatus = new GameStatus("HI");
        mNotificationCenter = NotificationCenter.getInstance();
        mGlobalCoolDown = GlobalCoolDown.getInstance();
        mPlayer = Player.getInstance();
        mRow = new Row();
        mShapeList = ShapeList.getInstance();
        mContext = context;
        mPool = Pool.getInstance();
        mGameStateController = new GameStateController(mShapeList,mContext,mCaptureZone,mRow, mGameStatus,mNotificationCenter);
        mGameStateRender = new GameStateRender(mShapeList,mCaptureZone, mRow,mGameStatus,mNotificationCenter);
    }

    /**
     * Ends the game and sends a broadcast to <code>GameActivity</code> to
     * show the <code>GameOverDialog</code>
     */
    public void endGame(){
        if (gameOver && count == 0) {
//
// do {
            count++;
            // Check the high score
            checkHighScore();
            // Create intent to end the game
            Intent intent = new Intent("end_game");
            // Bundle the current score and high score
            Bundle args = new Bundle();
            args.putInt("current_score", mPlayer.getHighscore());
            args.putInt("high_score", mGameStatus.getScore());
            // Add the arguments to the intent
            intent.putExtras(args);
            // Send the broadcast
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            // Only do this once
            // TODO: Use a better variable to accomplish this
//        }while(!gameOver);
        }
    }

    public void removeShapes(){
        List<Shape> currentShapes = mShapeList.getOnScreenShapes();
        if (!currentShapes.isEmpty()){
            for (Shape shape: currentShapes){
                mShapeList.removeShape(shape);
                mPool.addShape(shape);
            }
        }
    }

    /**
     * Resets the variables and states of the classes
     */
    public void resetVariables(){
        for (Shape shape: mShapeList.getOnScreenShapes()){
            shape.reset();
            mShapeList.removeShape(shape);
            mPool.addShape(shape);
        }
        gameOver = false;
        count = 0;
        Constants.CRITERIA_MODIFIER = 5.0f;
        CriteriaTimeBar.getInstance().reset();
        Constants.SPEED_MODIFIER = 1.0f;
        Constants.CURRENT_SPEED_MODIFIER = 1.0f;
        mGameStatus.reset();
        mNotificationCenter.reset();
    }

    /**
     * Sets the state of the Game
     * @param bool the state to set the Game
     */
    public void setGameOver(boolean bool){
        GameState.gameOver = bool;
    }

    /**
     * Checks if the game has ended
     * @return the state of the game
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * Generates a new criteria for the player
     */
    public void generateNewCriteria(){
        mGameStatus.generateNewCriteria();
        if (Constants.CRITERIA_MODIFIER - 0.1f < 0) {
            Constants.CRITERIA_MODIFIER = 0.1f;
        } else {
            Constants.CRITERIA_MODIFIER -= 0.1f;
        }
        Notification notification = new Notification.NotificationBuilder("NEW SHAPE")
                .location(new Location(Constants.SCREEN_WIDTH / 4, Constants.HEIGHT_NOTIFICATIONS))
                .time(Notification.MEDIUM)
                .direction(Notification.STATIONARY)
                .build();
        mNotificationCenter.addNotification(notification);
        // Reset Bar
        CriteriaTimeBar.getInstance().reset();
        // Activate buffer timer
        Buffer.getInstance().setActive();
    }


    public void increaseSpeed(){
        Constants.SPEED_MODIFIER += 0.3f;
        Constants.CURRENT_SPEED_MODIFIER += 0.3f;
        Notification notification = new Notification.NotificationBuilder("SPEED INCREASE")
                .location(new Location(Constants.SCREEN_WIDTH/4, Constants.HEIGHT_NOTIFICATIONS))
                .time(Notification.MEDIUM)
                .direction(Notification.STATIONARY)
                .build();
       mNotificationCenter.addNotification(notification);
    }

    public void updateBuffer(){
        if (Buffer.getInstance().isActive()){
            Buffer.getInstance().decreaseTime();
        }
    }

    public void updatePowerUps(){
        List<PowerUp> powerUpList = mPlayer.getPowerUps();
        if (!powerUpList.isEmpty()){
            for (PowerUp powerUp: powerUpList){
                if (powerUp.isActive()){
                    // Decrement time left
                    powerUp.decreaseTime();
                }
            }
        }
    }

    /**
     * Updates the alpha value of notifications in <code>NotificationCenter</code>
     */
    public void updateAnimations(){
        updateCoolDownBar();
        updateCriteriaTimeBar();

        List<Notification> notificationList = mNotificationCenter.getNotifications();
        if (!notificationList.isEmpty()){
            for(Notification notification: notificationList){
                // Decrement the alpha value
                notification.decrementAlpha();
            }
        }
        List<Shape> shapeList = mShapeList.getOnScreenShapes();
        if (!shapeList.isEmpty()){
            for(Shape shape: shapeList){
                shape.decrementScale();
                // Increment scale value
//                shape.incrementScale();
            }
        }
    }

    public void updateTimedObjects(){
        updateTime();
        updateNotifications();
//        updatePowerUps();
//        updateCooldown();
//        updateCriteriaTimeBar();
    }


    private void updateCriteriaTimeBar(){
        CriteriaTimeBar.getInstance().decreaseTime();
    }

    private void updateCoolDownBar(){
        // If a powerup has been activated
        if (CoolDownBar.getInstance().isActive()){
            CoolDownBar.getInstance().decreaseTime();
        }
    }

    /**
     * Updates the time left in the current game
     */
    private void updateTime(){
        mGameStatus.updateTime();
    }

    /**
     * Updates the time left existing notifications
     */
    private void updateNotifications(){
        List<Notification> notificationList = mNotificationCenter.getNotifications();
        if (!notificationList.isEmpty()){
            for (Notification notification: notificationList){
                // Decrement time left
                notification.decreaseTime();
            }
        }
    }

    private void updateCooldown(){
        // If the GlobalCoolDown has been activated
        if (CoolDownBar.getInstance().isActive()){
            // Then decreaseTime the time
            CoolDownBar.getInstance().decreaseTime();
        }
    }


    /**
     * Creates a row of random shapes
     * @param multiplier the Multipler to increase the speed of the shapes by
     */
    public void createShapeRow(Double multiplier){
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            new createShapeTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,multiplier);
        } else {
            new createShapeTask().execute(multiplier);
        }
        // TODO testing asynctask speeds
//        createShapeTask shapeTask = new createShapeTask();
//        shapeTask.execute(multiplier);
    }

    /**
     * Checks the touch event
     * @param x the X coordinate of the touch event
     * @param y the Y coordinate of the touch event
     */
    public void checkTouch(int x, int y){
        if (!gameOver){
            mGameStateController.checkTouch(x, y);
        }
    }

    /**
     * Calls the <code>update()</code> of the <code>GameStateController</code>
     */
    public void update(){
        mGameStateController.update();
    }

    /**
     * Calls the <code>draw()</code> of the <code>GameStateRenderer</code>
     * @param canvas the <code>Canvas</code> where the objects are drawn
     */
    public void draw(Canvas canvas){
        mGameStateRender.draw(canvas);
    }

    /**
     * Checks if the current score is higher than the current high score,
     * and updates the high score accordingly
     */
    private void checkHighScore(){
        int currentScore = mGameStatus.getScore();

        // Check if the current score is higher than the current highscore
        if (currentScore > mPlayer.getHighscore()){
            mPlayer.setHighscore(currentScore);
        }
    }


    /**
     * A <code>createShapeTask</code> extends <code>AsyncTask</code>
     * This async task gets a random row from the existing <code>Pool</code> of shapes
     * and adds them to the <code>ShapeList</code>
     */
    private static class createShapeTask extends AsyncTask<Double, Integer,List<android.graphics.drawable.shapes.Shape>> {

        @Override
        protected List<android.graphics.drawable.shapes.Shape> doInBackground(Double... params) {
            List<Shape> temp;
            int counter = 1;

            temp = mPool.getRandomRow();

            int nextColumn;
            boolean[] selectedColumns = new boolean[5];
            for(int i = 0; i < selectedColumns.length; i++){
                selectedColumns[i] = false;
            }

            for (int i = 0; i < temp.size(); i++){
                Random rand = new Random();

                nextColumn = rand.nextInt(Constants.ROW_SIZE) + 1;
                while (selectedColumns[nextColumn]){
                    nextColumn = rand.nextInt(Constants.ROW_SIZE) + 1;
                }
                selectedColumns[nextColumn] = true;


                if (nextColumn == 1){
                    temp.get(i).setLocation(Constants.COL_1 - Constants.BITMAP_WIDTH/2,Constants.SPAWN_AREA);
                }
                if (nextColumn == 2){
                    temp.get(i).setLocation(Constants.COL_2 - Constants.BITMAP_WIDTH/2,Constants.SPAWN_AREA);
                }
                if (nextColumn == 3){
                    temp.get(i).setLocation(Constants.COL_3 - Constants.BITMAP_WIDTH/2,Constants.SPAWN_AREA);
                }
                if (nextColumn == 4){
                    temp.get(i).setLocation(Constants.COL_4 - Constants.BITMAP_WIDTH/2,Constants.SPAWN_AREA);
                }
                mShapeList.addShape(temp.get(i));
            }

            /**
             * Uncomment for full rows
             */
            // Need to set location because random shapes are picked from the pool
//            for (Shape shape: temp){
////            for(int counter = 1; counter <= 4; counter++){
////                Shape shape = mPool.getRandomShape();
//                // If counter is larger than row size, deactivate to 1
//                if (counter > Constants.ROW_SIZE){
//                    counter = 1;
//                }
//                if (counter == 1){
//                    shape.setLocation(Constants.COL_1 - Constants.BITMAP_WIDTH/2,Constants.SPAWN_AREA);
//                }
//                if (counter == 2){
//                    shape.setLocation(Constants.COL_2 - Constants.BITMAP_WIDTH/2,Constants.SPAWN_AREA);
//                }
//                if (counter == 3){
//                    shape.setLocation(Constants.COL_3 - Constants.BITMAP_WIDTH/2,Constants.SPAWN_AREA);
//                }
//                if (counter == 4){
//                    shape.setLocation(Constants.COL_4 - Constants.BITMAP_WIDTH/2,Constants.SPAWN_AREA);
//                }
////                if (counter == 5){
//////                    shape.setLocation(Constants.SCREEN_WIDTH * 5/7 - GraphicLoader.category_1.getWidth()/3,Constants.SPAWN_AREA);
////                    shape.setLocation(Constants.COL_5 - GraphicLoader.category_1.getWidth()/2 ,Constants.SPAWN_AREA);
////                }
//                counter++;
////                shape.setSpeed(shape.getSpeed().getX(),Constants.SPEED * params[0]);
//                mShapeList.addShape(shape);
//            }
            return null;
        }

    }
}
