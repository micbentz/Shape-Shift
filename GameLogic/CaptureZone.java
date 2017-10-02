package com.game.bentz.ShapeShift.GameLogic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.game.bentz.ShapeShift.Utility.Constants;

/**
 * Created by Michael on 5/4/16.
 * Bottom of the screen where player must tap the correct shapes
 * TODO move Criterias to other class
 * TODO Add 2nd constructor for "easy" mode and initialize the orRequirements List
 */

public class CaptureZone implements Interactable, Drawable {
    private static final String TAG = "CaptureZone";
    private Rect bounds;

    public CaptureZone(){
        this.bounds = new Rect(0, Constants.SCREEN_HEIGHT * 7/10, Constants.SCREEN_WIDTH, Constants.SCREEN_END);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {

    }

    @Override
    public Rect getBounds(){
        return this.bounds;
    }

    @Override
    public boolean checkBounds(int x, int y) {
        if (this.bounds.contains(x,y)){
            return true;
        }
        return false;
    }

    @Override
    public void updateBounds(int x, int y) {

    }

}
