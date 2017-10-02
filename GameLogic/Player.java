package com.game.bentz.ShapeShift.GameLogic;

import com.game.bentz.ShapeShift.GameLogic.PowerUp.ClearScreenPowerUp;
import com.game.bentz.ShapeShift.GameLogic.PowerUp.DoublePointsPowerUp;
import com.game.bentz.ShapeShift.GameLogic.PowerUp.KeepCriteriaPowerUp;
import com.game.bentz.ShapeShift.GameLogic.PowerUp.PowerUp;
import com.game.bentz.ShapeShift.GameLogic.PowerUp.SlowDownPowerUp;
import com.game.bentz.ShapeShift.GameLogic.Store.Currency;
import com.game.bentz.ShapeShift.GameLogic.Store.Unlockable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Michael on 5/4/16.
 * The user who is playing the game
 */
public class Player {
    private final String TAG = this.toString();
    private static Player instance;

    private Integer highscore;
    private List<PowerUp> powerUpList;
    private Currency mCurrency;
    private Unlockable mUnlockable;

    private Player(){
        this.highscore = 0;
        this.powerUpList = new CopyOnWriteArrayList<>();
        initializePowerUps();
    }

    public static Player getInstance(){
        if (instance == null){
            instance = new Player();
        }
        return instance;
    }

    private void initializePowerUps(){
        powerUpList.add(new DoublePointsPowerUp(10,2));
        powerUpList.add(new SlowDownPowerUp(10,2));
        powerUpList.add(new KeepCriteriaPowerUp(10,2));
        powerUpList.add(new ClearScreenPowerUp(1,2));
    }

    public Integer getHighscore(){
        return highscore;
    }

    public void setHighscore(int score){
        this.highscore = score;
    }

    public List<PowerUp> getPowerUps(){
        return powerUpList;
    }
}
