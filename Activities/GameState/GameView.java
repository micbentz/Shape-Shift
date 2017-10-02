package com.game.bentz.ShapeShift.Activities.GameState;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Michael on 5/4/16.
 * A <code>GameView</code> holds the <code>DisplayThread</code>
 * Implements <code>SurfaceHolder.Callback</code> to allow user interaction
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private final String TAG = "GameView";

    private DisplayThread mDisplayThread;
//    private LogicThread mLogicThread;
    private Context mContext;

    /**
     * Constructor
     * @param context the <code>Context</code> of the parent activity
     */
    public GameView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    /**
     * Initializes the <code>GameView</code>.
     * Gets the holder and creates the <code>DisplayThread</code>
     */
    private void init(){
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        mDisplayThread = new DisplayThread(holder,mContext);
        setFocusable(true);
    }

    /**
     * Starts the thread when the surface is created
     * @param holder <code>Holder</code> for the view
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!mDisplayThread.isRunning()){
//            mLogicThread = new LogicThread(mContext);
            mDisplayThread = new DisplayThread(getHolder(),mContext);
//            mLogicThread.start();
            mDisplayThread.start();
        } else{
//            mLogicThread.start();
            mDisplayThread.start();
        }
    }

    /**
     * Not currently in use
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * Stops the thread when the surface is destroyed
     * @param holder the <code>Holder</code> for the view
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mDisplayThread.setRunning(false);
//        mLogicThread.setRunning(false);
//        mLogicThread.stopThread();
        mDisplayThread.stopThread();
    }
}
