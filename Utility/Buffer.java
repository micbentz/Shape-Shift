package com.game.bentz.ShapeShift.Utility;

/**
 * Created by Michael on 7/17/16.
 */
public class Buffer implements Timed {
    private final String TAG = "Buffer";
    private static Buffer instance;

    private final float BUFFER_TIME = 0.5f;
    private final float TIME_DECREMENT = 0.1f;
    private float time;
    private boolean isActive;

    private Buffer(){
        this.time = BUFFER_TIME;
        this.isActive = false;
    }

    public synchronized static Buffer getInstance(){
        if (instance == null){
            instance = new Buffer();
        }
        return instance;
    }

    public boolean isActive(){
        return isActive;
    }

    public void setActive(){
        isActive = true;
    }

    private void reset(){
        isActive = false;
        time = BUFFER_TIME;
    }


    @Override
    public Integer getTimer() {
        return null;
    }

    @Override
    public void decreaseTime(int time) {
    }

    @Override
    public void decreaseTime() {
        if(time - TIME_DECREMENT <= 0){
            reset();
        } else {
            time -= TIME_DECREMENT;
        }

    }
}
