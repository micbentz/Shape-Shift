package com.game.bentz.ShapeShift.Activities.GameState;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.game.bentz.ShapeShift.Utility.Buffer;
import com.game.bentz.ShapeShift.Utility.Constants;
import com.game.bentz.ShapeShift.Utility.CriteriaTimeBar;
import com.game.bentz.ShapeShift.Utility.GlobalCoolDown;


/**
 * Created by Michael on 5/10/16.
 * A <code>DisplayThread</code> contains the game loop
 * and updates the game logic via the <code>GameStateController</code>
 * and renders the updated states via the <code>GameStateRenderer</code>
 * TODO Move speed increase out of spawnTimer
 */
public class DisplayThread extends Thread {
    public final String TAG = "DisplayThread";

    private GameState mGameState;


    private final static int MAX_FPS = 30; //desired fps
    private final static int FRAME_PERIOD = 1000 / MAX_FPS; // the frame period

    // Delay amount between screen refreshes
    private final long  DELAY = 1;

    // Thread variables
    private boolean isRunning;
    private final SurfaceHolder mSurfaceHolder;
    private Context mContext;
    private Paint mBackgroundPaint;

    // Timer variables
    private float animateTimer;
    private float powerupTimer;
    private float spawnTimer;
    private float speedTimer;

    private float secondTimer;
    private float bufferTimer;
    private float criteriaTimer;

    // Update times for game conditions
//    private int count = 0;
    private float ANIMATION_TIME = Constants.ANIMATION_TIMER;
    private float BUFFER_TIME = 0.1f;
    private float SECOND = 1.0f;
    private float SPAWN_TIME = 0.8f;
    private float CRITERIA_TIME = 5.0f;
    private float SPEED_TIME = 10.0f;
    private float SPEED_MULTIPLIER = 1;

    /**
     * Constructor
     * @param surfaceHolder drawn on by <code>Canvas</code>
     * @param context
     */
    public DisplayThread(SurfaceHolder surfaceHolder, Context context) {
        this.mSurfaceHolder = surfaceHolder;
        this.mContext = context;
        this.mGameState = GameState.getInstance(context);

        // Black paint to clear the screen with
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setARGB(255, 0, 0, 0);
        isRunning = true;
    }


    /**
     * Overrides run of <code>Thread</code> class
     * Main game loop which updates the <code>GameState</code> class
     */
    @Override
    public void run() {
//        long startTime = System.nanoTime();
        long startTime = System.currentTimeMillis();

        // Reset the variables when starting the game
        mGameState.resetVariables();
        // Create criteria for the start of the game
        mGameState.generateNewCriteria();
        // Reset timers
        resetTimers();
        // Start the criteria time
        CriteriaTimeBar.getInstance().start((int)CRITERIA_TIME);

//        long startTime = System.currentTimeMillis();
        // Run until the thread is stopped
        while (isRunning) {
//            long startTime = System.nanoTime();
//          Makes deltaTime into fractions of seconds
//            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;


            // Lock the canvas
            Canvas canvas = mSurfaceHolder.lockCanvas(null);

            if (canvas != null) {
                // Clear the screen and draw objects to the screen
                synchronized (mSurfaceHolder) {
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mBackgroundPaint);
                    mGameState.draw(canvas);
                }
                // Unlock the canvas
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }

//            // Sleep the thread
//            try {
//                Thread.sleep(DELAY);
//            } catch (InterruptedException ex) {
//                //TODO: Log
//            }

            float deltaTime = (System.currentTimeMillis() - startTime);
            int sleepTime = (int) (FRAME_PERIOD - deltaTime);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException e) {
                }
            }
            while (sleepTime < 0) {
//                mGameState.update();
                sleepTime += FRAME_PERIOD;
            }

            deltaTime = (System.currentTimeMillis() - startTime);
            // .10 of a second /1000
//            deltaTime = (System.currentTimeMillis() - startTime) / 1000f;

            // Timers to keep track of conditions
            animateTimer += deltaTime;
            // If the game hasn't ended
            if (!mGameState.isGameOver()){

//                Log.d(TAG,"animate timer: " + f);
                if (animateTimer >= ANIMATION_TIME){
                    // Update the animations
                    mGameState.updateAnimations();
                    // Increment timers
                    secondTimer += Constants.ANIMATION_TIMER;
                    spawnTimer += Constants.ANIMATION_TIMER;
                    speedTimer += Constants.ANIMATION_TIMER;

                    if (Buffer.getInstance().isActive()){
                        bufferTimer += Constants.ANIMATION_TIMER;
                    }
                    // Increment timer if a powerup is active
                    if (GlobalCoolDown.getInstance().isActive()){
                        powerupTimer += Constants.ANIMATION_TIMER;
                    }
                    // Increment timer if keep criteria powerup is inactive
                    if (!Constants.KEEP_CRITERIA) {
                        criteriaTimer += Constants.ANIMATION_TIMER;
                    }
                    animateTimer = 0;
                }

                if (bufferTimer >= BUFFER_TIME){
                    mGameState.updateBuffer();
                    bufferTimer = 0;
                }

                if (secondTimer >= SECOND){
                    // Update second-sensitive objects
                    mGameState.updateTimedObjects();
                    // Reset the timer
                    secondTimer = 0;
                }

                if (powerupTimer >= SECOND){
                    mGameState.updatePowerUps();
                    powerupTimer = 0;
                }

                // Create a new row of shapes every x seconds
                if (spawnTimer >= SPAWN_TIME){
                    spawnTimer = 0;
                }

                // Change the criteria every x seconds and at the start of the game
                if (criteriaTimer >= Constants.CRITERIA_MODIFIER){
                    if (Constants.CRITERIA_MODIFIER > 2){
                        mGameState.generateNewCriteria();
                    }
                    // Reset the timer
                    criteriaTimer = 0;
                }

                if (speedTimer >= SPEED_TIME){
                    if (Constants.SPEED_MODIFIER < 5){
                        mGameState.increaseSpeed();
                        // Reset the timer
                        speedTimer = 0;
                    }
                }

                // Update the game objects
                mGameState.update();
            } else {
                // TODO add gameover animations i.e. GameState.endGame()
                // The game has ended
                mGameState.endGame();
            }
        }
    }

    private void resetTimers(){
        animateTimer = 0;
        powerupTimer = 0;
        spawnTimer = 0;
        speedTimer = 0;
    }

    /**
     * @return if the thread is running
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * @param state to set the thread to: "true" | "false"
     */
    public void setRunning(boolean state) {
        isRunning = state;
    }

    /**
     * Safely stops the thread
     */
    public  void stopThread(){
        // If game is already stopped, return
        if (!isRunning){
            return;
        }

        // Set running to false
        isRunning = false;

        // Safely stop the thread
        try{
            this.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
