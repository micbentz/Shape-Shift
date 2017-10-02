package com.game.bentz.ShapeShift.Utility;

/**
 * Created by Michael on 7/2/16.
 */
public class GlobalCoolDown {
    private static GlobalCoolDown instance;
    private boolean isActive;

    private GlobalCoolDown(){
        this.isActive = false;
    }

    public synchronized static GlobalCoolDown getInstance(){
        if (instance == null){
            instance = new GlobalCoolDown();
        }
        return instance;
    }

    public void startCountdown(){
        isActive = true;
    }

    public boolean isActive(){
        return isActive;
    }

    public void resetCountdown(){
        isActive = false;
    }
}
