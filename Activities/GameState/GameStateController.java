package com.game.bentz.ShapeShift.Activities.GameState;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.game.bentz.ShapeShift.GameLogic.CaptureZone;
import com.game.bentz.ShapeShift.GameLogic.Criteria.Criteria;
import com.game.bentz.ShapeShift.GameLogic.GameStatus;
import com.game.bentz.ShapeShift.GameLogic.Player;
import com.game.bentz.ShapeShift.GameLogic.PowerUp.PowerUp;
import com.game.bentz.ShapeShift.GameLogic.Row;
import com.game.bentz.ShapeShift.GameLogic.Shape.Shape;
import com.game.bentz.ShapeShift.Utility.Buffer;
import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.CoolDownBar;
import com.game.bentz.ShapeShift.Utility.GlobalCoolDown;
import com.game.bentz.ShapeShift.Utility.Location;
import com.game.bentz.ShapeShift.Utility.Notification;
import com.game.bentz.ShapeShift.Utility.NotificationCenter;
import com.game.bentz.ShapeShift.Utility.Pool;
import com.game.bentz.ShapeShift.Utility.ShapeList;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Michael on 5/4/16.
 * A <code>GameStateController</code> updates the game logic and the state of the objects
 * TODO Add way to have lives/time options and respective lose condition
 * TODO Encapsulate creating a notification
 */
public class GameStateController {
    private final String TAG = "GameStateController";
    private Pool mPool;
    private CaptureZone mCaptureZone;
    private Row mRow;
    private GameStatus mGameStatus;
    private NotificationCenter mNotificationCenter;
    private ShapeList mShapeList;
    private CoolDownBar mCoolDownBar;
    private Player mPlayer;
    private List<Shape> criteriaShapes;
    private List<Shape> nonCriteriaShapes;
    private Context mContext;

    /**
     * Constructor
     * @param captureZone the <code>CaptureZone</code> where touch events are checked
     * @param gameStatus the <code>GameStatus</code> which holds the criteria, score and time left
     */
    public GameStateController(ShapeList shapeList, Context context, CaptureZone captureZone, Row row, GameStatus gameStatus, NotificationCenter notificationCenter){
        this.mContext = context;
        this.mCaptureZone = captureZone;
        this.mRow = row;
        this.mGameStatus = gameStatus;
        this.mNotificationCenter = notificationCenter;
        this.mShapeList = shapeList;
        this.mCoolDownBar = CoolDownBar.getInstance();
        this.mPlayer = Player.getInstance();
        this.mPool = Pool.getInstance();
    }

    /**
     * Updates the states of all game objects
     */
    public void update(){
        // Check lose condition
        checkLoseCondition();
        // Update Criteria
        updateCriteria();
        // Update Row
        updateRow();
        // Update Shapes
        updateShapes();
        // Update Notifications
        updateNotifications();
        // Update PowerUps
//        updatePowerUps();
    }

    /**
     * Checks the touch event and either increments the score and timer or
     * decrements the score and timer
     * @param x the X coordinate of the touch event
     * @param y the Y coordinate of the touch event
     */
    public void checkTouch(int x, int y){
        handleCaptureZoneTouch(x,y);
        handlePowerUpTouch(x,y);
    }

    private void handleCaptureZoneTouch(int x, int y){
//        if (mCaptureZone.checkBounds(x,y)){
            for(Iterator<Shape> iterator = criteriaShapes.iterator(); iterator.hasNext();){
                Shape shape = iterator.next();
                if (shape.checkBounds(x,y)){
                    // Reset the shape bounds and location
                    resetShape(shape);
                    // Remove shape from the ShapeList and add to pool
                    addToPool(shape);
                    // Increase the time
//                    mGameStatus.increaseTime();
                    // Update score
                    mGameStatus.incrementScore();
                    // Create a notification
                    addCorrectNotification(shape);
                    // Play right sound
                    if (Constants.FX_ACTIVE){
//                        MediaPlayer mp = MediaPlayer.create(mContext,R.raw.softpop);
//                        mp.start();
//                        playSoundTask playSoundTask = new playSoundTask();
//                        playSoundTask.execute(R.raw.softpop,mContext);
                    }
                }
            }
            for(Iterator<Shape> iterator = nonCriteriaShapes.iterator(); iterator.hasNext();){
                Shape shape = iterator.next();
                if (shape.checkBounds(x,y)){
                    // Reset the shape bounds and location
                    resetShape(shape);
                    // Remove shape from the ShapeList and add to pool
                    addToPool(shape);
                    // Decrease the time
//                    mGameStatus.decreaseTime();
                    mGameStatus.loseLife();
                    // Create a notification
//                    addIncorrectNotification("Wrong shape",shape);
                    // Play wrong sound
                    if (Constants.FX_ACTIVE){
//                        MediaPlayer mp = MediaPlayer.create(mContext,R.raw.buzz);
//                        mp.start();
//                        playSoundTask playSoundTask = new playSoundTask();
//                        playSoundTask.execute(R.raw.buzz,mContext);
                    }
                }
            }
//        }
    }

    private void handlePowerUpTouch(int x, int y){
        // Get the user's PowerUps
        List<PowerUp> powerUpList = mPlayer.getPowerUps();
        // If the list isn't empty
        if (!powerUpList.isEmpty()){
            if (!GlobalCoolDown.getInstance().isActive()){
                for(PowerUp powerUp: powerUpList){
                    if (powerUp.checkBounds(x,y)){
                        // Activate the PowerUp
                        powerUp.activate();
                        mCoolDownBar.start(powerUp.getTimer());
                        // Add the notification
                        mNotificationCenter.addNotification(powerUp.getNotification());
                    }
                }
            }
        }
    }

    /**
     * Checks if the lose condition has been met
     */
    private void checkLoseCondition(){
        if (mGameStatus.getTimeLeft() <= 0){
            Log.d(TAG,"You've lost");
            GameState.getInstance().setGameOver(true);
        }
        if (mGameStatus.getLivesLeft() <= 0){
            GameState.getInstance().setGameOver(true);
        }
    }

    private void updateRow(){
//        mRow.setLocation(0,mRow.getLocation().getY() + Constants.getDP((int)(mRow.getSpeed().getY() * Constants.SPEED_MODIFIER)));
        mRow.setLocation(0,mRow.getLocation().getY() + (int)(mRow.getSpeed().getY() * Constants.SPEED_MODIFIER));
        if (mRow.getLocation().getY() >= Constants.BITMAP_WIDTH){
                GameState.getInstance().createShapeRow(1.0);
                mRow.reset();
            }
    }

    /**
     * Updates the state of the shapes on screen.
     * Moves them down the screen and adds them to the <code>Pool</code>
     * if they move offscreen
     */
    private void updateShapes(){
        // For every shape currently on screen
        for (Iterator<Shape> iterator = mShapeList.getOnScreenShapes().iterator(); iterator.hasNext();){
            Shape shape = iterator.next();
            // Update the location of the shape
            shape.updateBounds(shape.getLocation().getX(),shape.getLocation().getY());
            //TODO dont need getPhysicalPx already handled in initial speed
//            shape.setLocation(shape.getLocation().getX(),shape.getLocation().getY() + Constants.getDP((int)(shape.getSpeed().getY() * Constants.SPEED_MODIFIER)));
            shape.setLocation(shape.getLocation().getX(),shape.getLocation().getY() + (int)(shape.getSpeed().getY() * Constants.SPEED_MODIFIER));
            // Update the bounds of the shape

            // Check if the shape went below the screen
            if (shape.getLocation().getY() >= Constants.SCREEN_END){
                // If the shape was of the criteria type
                if (criteriaShapes.contains(shape)){
                    // If the buffer isn't active
                    if (!Buffer.getInstance().isActive()) {
                        // Decrease the time
                        mGameStatus.loseLife();
//                    mGameStatus.decreaseTime();
                        // Create a notification
//                        addIncorrectNotification("Missed Shape", shape);
                        // Play wrong sound
                        if (Constants.FX_ACTIVE) {
//                        MediaPlayer mp = MediaPlayer.create(mContext,R.raw.buzz);
//                        mp.start();
//                        playSoundTask playSoundTask = new playSoundTask();
//                        playSoundTask.execute(R.raw.buzz,mContext);
                        }
                    }

                }
                // Reset the shape bounds and location
                resetShape(shape);
                // Remove shape from list and to the pool
                addToPool(shape);
            }
        }
    }

    /**
     * Updates the criteria and the corresponding valid and invalid shapes
     */
    private void updateCriteria() {
        // Get the current criteria
        Criteria currentCriteria = mGameStatus.getCurrentCriteria();
        if (!Constants.GAMEMODE_REVERSE){
            // Update the list with all correct shapes
            criteriaShapes = currentCriteria.meetCriteria(mShapeList.getOnScreenShapes());
            // Update the list with all incorrect shapes
            nonCriteriaShapes = currentCriteria.doesNotMeetCriteria(mShapeList.getOnScreenShapes());
        } else{
            // Swap the criteria and nonCriteria Shapes
            criteriaShapes = currentCriteria.doesNotMeetCriteria(mShapeList.getOnScreenShapes());
            nonCriteriaShapes = currentCriteria.meetCriteria(mShapeList.getOnScreenShapes());
        }
    }

    /**
     * Updates the notifications, removes them if the timer has expired
     */
    private void updateNotifications() {
        List<Notification> notificationList = mNotificationCenter.getNotifications();
        // Check if the list is empty
        if (!mNotificationCenter.isEmpty()) {
            for(Notification notification: notificationList){
                // If the time has expired remove the notification
                if (notification.getTimer() <= 0){
                    mNotificationCenter.removeNotification(notification);
                }
                // Update the location
//                notification.setLocation(notification.getLocation().getX() + Constants.SPEED /2 * notification.getDirection(),notification.getLocation().getY() - Constants.SPEED /2);
                notification.setLocation(notification.getLocation().getX() + Constants.SPEED /2 * notification.getDirection(),notification.getLocation().getY() - Constants.SPEED /2 * notification.getDirection());
            }
        }
    }

    private void addIncorrectNotification(String text, Shape shape){
//        "- " + Constants.TIME_DECREASE.toString()
        Notification notification = new Notification.NotificationBuilder(text)
//                .location(new Location(shape.getLocation().getX(),Constants.SCREEN_HEIGHT * 1/7))
                .location(new Location(Constants.SCREEN_WIDTH/2 - Constants.SCREEN_WIDTH/4,Constants.SCREEN_HEIGHT/2))
                .time(Notification.SHORT)
                .direction(Notification.STATIONARY)
                .build();
        mNotificationCenter.addNotification(notification);
    }

    private void addCorrectNotification(Shape shape){
//        Notification notification = new Notification.NotificationBuilder("+ " + Constants.TIME_INCREASE.toString())
        Integer point = 1;
        point *= Constants.POINT_MODIFIER;
        Notification notification = new Notification.NotificationBuilder("+ " + point.toString())
//                .location(new Location(shape.getLocation().getX(),Constants.SCREEN_HEIGHT * 1/7))
                .location(new Location(Constants.SCREEN_WIDTH/2,Constants.SCREEN_HEIGHT/2))
                .time(Notification.SHORT)
                .direction(Notification.STATIONARY)
                .build();
        mNotificationCenter.addNotification(notification);
    }

    private void addToPool(Shape shape){
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            new populatePoolTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,shape);
        } else {
            new populatePoolTask().execute(shape);
        }
    }


//        private void updatePowerUps(){
//         Get the user's powerups
//        List<PowerUp> powerUpList = mPlayer.getPowerUps();
//        // If the list isn't empty
//        if (!powerUpList.isEmpty()){
//            for (PowerUp powerUp: powerUpList){
//                // If the time has expired
//                if (powerUp.getTimer() == 0){
//                    // Deactivate the powerup
////                    powerUp.deactivate();
////                   mCoolDownBar.reset();
//                }
//            }
//        }
//     }

    private void resetShape(Shape shape){
        shape.updateBounds(shape.getLocation().getX(),Constants.SPAWN_AREA);
        shape.setLocation(shape.getLocation().getX(),Constants.SPAWN_AREA);
    }

    private class populatePoolTask extends AsyncTask<Shape,Integer,Integer>{
        @Override
        protected Integer doInBackground(Shape... params){
            Shape shape = params[0];
//            shape.updateBounds(shape.getLocation().getX(),Constants.SPAWN_AREA);
//            shape.setLocation(shape.getLocation().getX(),Constants.SPAWN_AREA);
            shape.resetScale();
            mShapeList.removeShape(shape);
            mPool.addShape(shape);
            return 0;
        }
    }

    private class playSoundTask extends AsyncTask<Object, Integer, Integer>{
        @Override
        protected Integer doInBackground(Object... params){
            MediaPlayer mp = MediaPlayer.create((Context)params[1], (int)params[0]);
            mp.start();
            return null;
        }
    }


}
